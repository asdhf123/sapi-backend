package com.syl.sapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.syl.sapicommon.model.entity.User;

/**
 * 给网关调用的内部用户服务
 *
 * @author lenovo
 */
public interface InnerUserService {


    /**
     * 查询用户是否分配密钥
     * @param accessKey
     * @return
     */
    //1.数据库查是否分配给用户密钥
    User getInvokeUser(String accessKey);
}
