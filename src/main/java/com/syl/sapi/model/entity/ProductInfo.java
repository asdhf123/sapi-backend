package com.syl.sapi.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品信息
 * @TableName product_info
 */
@TableName(value ="product_info")
@Data
public class ProductInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 价格
     */
    private Long price;

    /**
     * 对应积分数量
     */
    private Long pointsNumber;

    /**
     * 商品状态（0- 下线(默认) 1- 上线）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}