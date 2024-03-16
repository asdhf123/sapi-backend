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
        return "Post 你的用户名是"+user.getUsername();
    }
}
