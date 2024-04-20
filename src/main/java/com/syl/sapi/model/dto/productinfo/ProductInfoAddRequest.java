package com.syl.sapi.model.dto.productinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class ProductInfoAddRequest implements Serializable {
    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品描述
     */
    private String description;


    /**
     * 价格
     */
    private Long price;

    /**
     * 对应积分数量
     */
    private Long pointsNumber;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}