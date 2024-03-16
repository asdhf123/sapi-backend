package com.syl.sapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syl.sapi.common.ErrorCode;
import com.syl.sapi.exception.BusinessException;
import com.syl.sapi.mapper.InterfaceInfoMapper;
import com.syl.sapi.service.InterfaceInfoService;
import com.syl.sapicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-03-01 17:25:40
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();

        // 创建时，参数不能为空
        if (add) {
            if(StringUtils.isAllBlank(name)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
    }
}




