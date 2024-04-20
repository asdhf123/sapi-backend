package com.syl.sapi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syl.sapi.common.ErrorCode;
import com.syl.sapi.exception.BusinessException;
import com.syl.sapi.mapper.ProductInfoMapper;
import com.syl.sapi.model.entity.ProductInfo;
import com.syl.sapi.service.ProductInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【product_info(产品信息)】的数据库操作Service实现
* @createDate 2024-04-20 12:25:19
*/
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo>
    implements ProductInfoService {

    @Override
    public void validProductInfo(ProductInfo productInfo, boolean b) {
        if(productInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = productInfo.getName();
        String description = productInfo.getDescription();
        if(StringUtils.isAllBlank(name,description)){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //参数校验
        if(name.length()>20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"名称过长");
        }
        if(description.length()>50){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"描述过长");
        }
    }
}




