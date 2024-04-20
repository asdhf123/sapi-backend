package com.syl.sapi.model.dto.productinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.syl.sapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author lenovo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductInfoQueryRequest extends PageRequest implements Serializable {

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