package com.syl.sapiinterface.controller;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.json.JSONUtil;

import com.syl.sapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询名称 API
 * @author syl
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name){
        return "GET 你的名字是"+name;
    }
    @PostMapping("/")
    public String getNameByPost(@RequestParam String name){
        return "Post 你的名字是"+name;
    }
    @PostMapping("/user")//json方式传参
    public String getUsernameByGet(@RequestBody User user, HttpServletRequest request){
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timeStamp = request.getHeader("timeStamp");
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        //todo：从数据库读取key
        if(!accessKey.equals("Zhangsan")){
            throw new RuntimeException("key不正确");
        }
        //todo:从数据库读取随机数
        if(nonce.length()>4){
            throw new RuntimeException("随机数不正确");
        }
        //获取目前时间，运算时差
        long nowTime = System.currentTimeMillis()/1000;
        long time = Long.valueOf(timeStamp)-nowTime;
        if(time>300){
            throw new RuntimeException("超时");
        }
        //检验body
        String jsonStr = JSONUtil.toJsonStr(user);
        if(!body.equals(jsonStr)){
            throw new RuntimeException("用户参数不正确");
        }
        //签名认证,从数据库拿密码
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String digestHex = md5.digestHex(body+12345678);
        if(!digestHex.equals(sign)){
            throw new RuntimeException("签名异常");
        }
        return "Post 你的用户名是"+user.getUsername();
    }
}
