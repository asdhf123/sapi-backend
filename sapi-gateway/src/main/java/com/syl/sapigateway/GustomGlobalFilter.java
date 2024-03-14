package com.syl.sapigateway;

import cn.hutool.json.JSONObject;
import com.syl.sapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class GustomGlobalFilter implements GlobalFilter, Ordered {
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.请求日志

        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识" + request.getId());
        log.info("请求路径" + request.getPath().value());
        log.info("请求方法" + request.getMethod());
        log.info("请求参数" + request.getQueryParams());
        log.info("主机名" + request.getLocalAddress().getHostString());
        log.info("请求来源地址" + request.getRemoteAddress());
        HttpHeaders headers = request.getHeaders();
        //
        //3.黑白名单
        ServerHttpResponse response = exchange.getResponse();
        String host = request.getLocalAddress().getHostString();
        if (!IP_WHITE_LIST.contains(host)) {
            handleNoAuth(response);
        }
        //4.用户鉴权(判断ak，sk是否合法)
        String accessKey = headers.getFirst("accessKey");
        //todo:从数据库中获取
        if (!accessKey.equals("Zhangsan")) {
            handleNoAuth(response);
        }
        String nonce = headers.getFirst("nonce");
        if (Long.valueOf(nonce) > 10000L) {
            handleNoAuth(response);
        }
        String timeStamp = headers.getFirst("timeStamp");
        final Long FIVE_MINUTES = 60 * 5L;
        if (System.currentTimeMillis() / 1000 - Long.valueOf(timeStamp) >= FIVE_MINUTES) {
            handleNoAuth(response);
        }
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        //todo：从数据库中获得secretKey
        String ServerSign = SignUtils.getSign(body,"12345678");
        if(!ServerSign.equals(sign)){
            handleNoAuth(response);
        }
        //5.请求模拟接口是否存在
        //todo:远程调用去数据库查询
        //6.请求转发，调用模拟接口
        return handleResponse(exchange,chain);
    }
    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void>  writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        //释放掉内存
                                        DataBufferUtils.release(dataBuffer);
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        //data
                                        String data = new String(content, StandardCharsets.UTF_8);
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 降级处理返回数据
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }



    @Override
    public int getOrder() {
        return -2;
    }
}
