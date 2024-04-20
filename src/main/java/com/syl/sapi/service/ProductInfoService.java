package com.syl.sapi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.syl.sapi.model.entity.ProductInfo;

/**
* @author lenovo
* @description 针对表【product_info(产品信息)】的数据库操作Service
* @createDate 2024-04-20 12:25:19
*/
public interface ProductInfoService extends IService<ProductInfo> {

    void validProductInfo(ProductInfo productInfo, boolean b);
}
