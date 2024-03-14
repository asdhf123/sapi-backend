package com.syl.sapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.syl.sapi.common.ErrorCode;
import com.syl.sapi.exception.BusinessException;
import com.syl.sapi.mapper.UserInterfaceInfoMapper;
import com.syl.sapi.model.entity.UserInterfaceInfo;
import com.syl.sapi.service.UserInterfaceInfoService;

import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-03-10 22:51:15
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，参数不能为空
        if (add) {
            if(userInterfaceInfo.getInterfaceInfoId()<0 || userInterfaceInfo.getUserId()<0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
            }
        }
        // 有参数则校验
        if (userInterfaceInfo.getLeftNum()<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }

    }

    /**
     * 用户调用接口统计数+1
     *
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if(interfaceInfoId<0 || userId<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.gt("leftNum",0);
        //todo:加锁
        updateWrapper.setSql("leftNum = leftNum-1,totalNum = totalNum+1");
        boolean update = this.update(updateWrapper);
        return update;
    }

}




