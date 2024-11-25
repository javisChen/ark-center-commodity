package com.ark.center.product.infra.spu;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShelfStatus implements BasicEnums {
    DOWN(0, "下架"),
    UP(1, "上架"),
    ;
    private final Integer value;
    private final String text;

    public static ShelfStatus getByValue(Integer value) {
        return EnumUtils.getByValue(values(), value);
    }

    public static String getText(Integer value) {
        return EnumUtils.getTextByValue(values(), value);
    }
}
