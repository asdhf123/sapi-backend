package com.syl.sapicommon.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.syl.sapicommon.model.entity.InterfaceInfo;

/**
* @author lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-03-01 17:25:40
*/
public interface InnerInterfaceInfoService {

    //2.从数据库中查询模拟接口是否存在

    /**
     * 查询接口是否存在
     * @param requestPath
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String requestPath, String method);

}
