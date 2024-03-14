package com.syl.sapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.syl.sapi.model.entity.UserInterfaceInfo;


/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-03-10 22:51:15
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    boolean invokeCount(long interfaceInfoId, long userId);
}
