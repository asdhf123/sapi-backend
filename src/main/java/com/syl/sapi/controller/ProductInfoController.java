package com.syl.sapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.syl.sapi.annotation.AuthCheck;
import com.syl.sapi.common.*;
import com.syl.sapi.constant.CommonConstant;
import com.syl.sapi.exception.BusinessException;
import com.syl.sapi.model.dto.productinfo.ProductInfoAddRequest;
import com.syl.sapi.model.dto.productinfo.ProductInfoQueryRequest;
import com.syl.sapi.model.dto.productinfo.ProductInfoUpdateRequest;
import com.syl.sapi.model.entity.ProductInfo;
import com.syl.sapi.model.enums.ProductInfoStatusEnum;
import com.syl.sapi.service.ProductInfoService;
import com.syl.sapi.service.UserService;
import com.syl.sapicommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/shop")
@Slf4j
@RestController
public class ProductInfoController {

    @Resource
    private ProductInfoService productInfoService;
    @Resource
    private UserService userService;

    /**
     * 创建
     *
     * @param productInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addProductInfo(@RequestBody ProductInfoAddRequest productInfoAddRequest, HttpServletRequest request) {
        if (productInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productInfoAddRequest, productInfo);
        // 校验
        productInfoService.validProductInfo(productInfo, true);
        User loginUser = userService.getLoginUser(request);
        productInfo.setUserId(loginUser.getId());
        boolean result = productInfoService.save(productInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newProductInfoId = productInfo.getId();
        return ResultUtils.success(newProductInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProductInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ProductInfo oldProductInfo = productInfoService.getById(id);
        if (oldProductInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldProductInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = productInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param productInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateProductInfo(@RequestBody ProductInfoUpdateRequest productInfoUpdateRequest,
                                                     HttpServletRequest request) {
        if (productInfoUpdateRequest == null || productInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productInfoUpdateRequest, productInfo);
        productInfo.setUserId(user.getId());
        // 参数校验
        productInfoService.validProductInfo(productInfo, false);

        long id = productInfoUpdateRequest.getId();
        // 判断是否存在
        ProductInfo oldProductInfo = productInfoService.getById(id);
        if (oldProductInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldProductInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = productInfoService.updateById(productInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ProductInfo> getProductInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProductInfo productInfo = productInfoService.getById(id);
        return ResultUtils.success(productInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param productInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<ProductInfo>> listProductInfo(ProductInfoQueryRequest productInfoQueryRequest) {
        ProductInfo productInfoQuery = new ProductInfo();
        if (productInfoQueryRequest != null) {
            BeanUtils.copyProperties(productInfoQueryRequest, productInfoQuery);
        }
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>(productInfoQuery);
        List<ProductInfo> productInfoList = productInfoService.list(queryWrapper);
        return ResultUtils.success(productInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param productInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ProductInfo>> listProductInfoByPage(ProductInfoQueryRequest productInfoQueryRequest, HttpServletRequest request) {
        if (productInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProductInfo productInfoQuery = new ProductInfo();
        BeanUtils.copyProperties(productInfoQueryRequest, productInfoQuery);
        long current = productInfoQueryRequest.getCurrent();
        long size = productInfoQueryRequest.getPageSize();
        String sortField = productInfoQueryRequest.getSortField();
        String sortOrder = productInfoQueryRequest.getSortOrder();
        String description = productInfoQuery.getDescription();
        // description 需支持模糊搜索
        productInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>(productInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<ProductInfo> productInfoPage = productInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(productInfoPage);
    }

    // endregion
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineProductInfo(@RequestBody IdRequest idRequest) {
        Long id = idRequest.getId();
        if(idRequest == null || id<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验商品是否存在
        ProductInfo productInfo = productInfoService.getById(id);
        if(productInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //修改商品状态
        productInfo.setStatus(ProductInfoStatusEnum.ONLINE.getValue());
        boolean result = productInfoService.updateById(productInfo);
        return ResultUtils.success(result);
    }

    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineProductInfo(@RequestBody IdRequest idRequest) {
        Long id = idRequest.getId();
        if(idRequest == null || id<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验商品是否存在
        ProductInfo productInfo = productInfoService.getById(id);
        if(productInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //修改商品状态
        productInfo.setStatus(ProductInfoStatusEnum.OFFLINE.getValue());
        boolean result = productInfoService.updateById(productInfo);
        return ResultUtils.success(result);
    }

}
