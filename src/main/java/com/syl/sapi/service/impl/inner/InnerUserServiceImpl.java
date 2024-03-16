package com.syl.sapi.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.syl.sapi.common.ErrorCode;
import com.syl.sapi.exception.BusinessException;
import com.syl.sapi.mapper.UserMapper;
import com.syl.sapicommon.model.entity.User;
import com.syl.sapicommon.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getInvokeUser(String accessKey) {
        if(StrUtil.isBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("accessKey",accessKey);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }
}
