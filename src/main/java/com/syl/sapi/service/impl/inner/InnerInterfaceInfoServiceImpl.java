package com.syl.sapi.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.syl.sapi.common.ErrorCode;
import com.syl.sapi.exception.BusinessException;
import com.syl.sapi.mapper.InterfaceInfoMapper;


import com.syl.sapicommon.model.entity.InterfaceInfo;
import com.syl.sapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String requestPath, String method) {
        if(StringUtils.isAllBlank(requestPath,method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",requestPath);
        queryWrapper.eq("method",method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        if(interfaceInfo == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"无此接口");
        }
        return interfaceInfo;
    }
}
