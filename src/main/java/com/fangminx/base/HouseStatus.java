package com.fangminx.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源状态
 */
@AllArgsConstructor
@Getter
public enum  HouseStatus {
    NOT_AUDITED(0), //未审核
    PASSES(1), //审核通过
    RENTED(2), //已出租
    DELETED(3), //逻辑删除
    ;
    private int value;
}
