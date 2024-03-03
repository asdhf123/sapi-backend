package com.syl.sapi.service;

import com.syl.sapi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author lenovo
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    void userRegister() {
        String userAccount = "Zhangsan";
        String userPassword = "";
        String checkPassword = "123456";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "z";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "Zhangsan";
            userPassword = "123456";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "Zhang san";
            userPassword = "12345678";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            checkPassword = "123456789";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "Lisi";
            checkPassword = "12345678";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "Zhagnsan";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }
}