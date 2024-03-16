package com.syl.sapi.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.syl.sapi.mapper.InterfaceInfoMapper;
import com.syl.sapiclientsdk.client.SapiClient;
import com.syl.sapiclientsdk.model.User;


import com.syl.sapicommon.model.entity.InterfaceInfo;
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
    @Resource
    InterfaceInfoMapper interfaceInfoMapper;

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
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",3);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        System.out.println(interfaceInfo);
    }

}

