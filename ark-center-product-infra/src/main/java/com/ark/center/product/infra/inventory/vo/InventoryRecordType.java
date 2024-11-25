package com.ark.center.product.infra.inventory.vo;

import lombok.Getter;

/**
 * 库存操作记录类型
 */
@Getter
public enum InventoryRecordType {

    /**
     * 可用库存 -
     * 预扣库存 +
     */
    PRE_DEDUCT("库存预扣", ""),

    /**
     * 预扣库存 -
     * 占用库存 +
     */
    DEDUCT( "库存扣减", ""),

    /**
     * 占用库存 -
     */
    DELIVER( "库存发货", ""),

    ;

    private final String name;
    private final String desc;

    InventoryRecordType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
