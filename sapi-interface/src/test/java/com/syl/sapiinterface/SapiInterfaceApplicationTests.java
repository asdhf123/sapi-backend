package com.syl.sapiinterface;

import com.syl.sapiclientsdk.client.SapiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SapiInterfaceApplicationTests {
    @Resource
    SapiClient sapiClient;

    @Test
    void contextLoads() {
        String username = sapiClient.getNameByPost("s");
        System.out.println("username");
    }

}
