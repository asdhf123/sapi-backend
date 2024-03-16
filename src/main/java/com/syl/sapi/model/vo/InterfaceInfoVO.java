package com.syl.sapi.model.vo;

import com.syl.sapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口调用数据封装类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfoVO extends InterfaceInfo {
    private Integer totalNum;
    private static final long serialVersionUID = 1L;

}
