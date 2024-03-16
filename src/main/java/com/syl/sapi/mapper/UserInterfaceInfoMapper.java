package com.syl.sapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syl.sapi.model.vo.InterfaceInfoVO;
import com.syl.sapicommon.model.entity.InterfaceInfo;
import com.syl.sapicommon.model.entity.UserInterfaceInfo;

import java.util.List;


/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-03-10 22:51:15
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    /**
     * 查询top<limit>的接口和调用次数
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




