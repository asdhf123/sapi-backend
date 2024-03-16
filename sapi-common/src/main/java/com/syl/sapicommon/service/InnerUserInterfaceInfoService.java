package com.syl.sapicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.syl.sapicommon.model.entity.UserInterfaceInfo;


/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-03-10 22:51:15
*/
public interface InnerUserInterfaceInfoService {

    //调用次数+1
    boolean invokeCount(long interfaceInfoId, long userId);
}
