package com.syl.sapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.syl.sapiclientsdk.model.User;


import java.util.HashMap;
import java.util.Map;

public class SapiClient {
    private String accessKey;
    private String secretKey;

    public SapiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);

        return result;
    }

    public String getSign(String json) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String testStr = json+secretKey;
        String digestHex = md5.digestHex(testStr);
        return digestHex;
    }

    public Map getMap(String json) {
        Map hashMap = new HashMap();
        hashMap.put("accessKey", accessKey);
        hashMap.put("nonce", String.valueOf(RandomUtil.randomInt(4)));
        hashMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("body", json);
        hashMap.put("sign",getSign(json));
        return hashMap;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        String body = HttpRequest.post("http://localhost:8123/api/name/user/")
                .addHeaders(getMap(json))
                .body(json)
                .execute().body();
        return body;
    }
}
