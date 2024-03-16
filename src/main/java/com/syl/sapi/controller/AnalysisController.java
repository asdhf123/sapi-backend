package com.syl.sapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.syl.sapi.annotation.AuthCheck;
import com.syl.sapi.common.BaseResponse;
import com.syl.sapi.common.ResultUtils;
import com.syl.sapi.mapper.InterfaceInfoMapper;
import com.syl.sapi.mapper.UserInterfaceInfoMapper;
import com.syl.sapi.model.vo.InterfaceInfoVO;
import com.syl.sapicommon.model.entity.InterfaceInfo;
import com.syl.sapicommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;
    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){
        //获得top的interfaceId和tolNum
        List<UserInterfaceInfo> interfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        //根据interfaceInfoList得到每一个值放入VO中
        List<InterfaceInfoVO> res = new ArrayList<>();
        for (UserInterfaceInfo userInterfaceInfo : interfaceInfoList) {
            QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userInterfaceInfo.getInterfaceInfoId());
            InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo,interfaceInfoVO);
            interfaceInfoVO.setTotalNum(userInterfaceInfo.getTotalNum());
            res.add(interfaceInfoVO);
        }

        return ResultUtils.success(res);
    }
}
