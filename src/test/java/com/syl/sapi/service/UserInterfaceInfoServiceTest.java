package com.syl.sapi.service;


import com.google.gson.Gson;
import com.syl.sapiclientsdk.client.SapiClient;
import com.syl.sapiclientsdk.model.User;

import com.syl.sapicommon.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;



@SpringBootTest
public class UserInterfaceInfoServiceTest {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Resource
    SapiClient sapiClient;

    @Test
    public void invokeCount() {
        //todo:注意这里要引入jupiter包中的junit
        boolean b = userInterfaceInfoService.invokeCount(7L, 3L);
        Assertions.assertEquals(true,b);
    }
    @Test
    public void userSdk(){
        User user = new User("叶良辰");
        String usernameByPost = sapiClient.getUsernameByPost(user);
    }
    @Test
    public void test(){
        Gson gson = new Gson();
        //将获得的json串转为user对象
        String json = "{\"username\":\"jack\"}";
        User1 user = gson.fromJson(json,User1.class);
        System.out.println(user);
    }

}

class User1{
    String username;
}