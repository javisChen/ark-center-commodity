package com.ark.center.commodity.domain.attr.vo;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class AttrOption {

    private Long attrId;

    private Long spuId;

    private String value;

    private Type type;

    public AttrOption(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    public AttrOption() {
    }

    @Getter
    @AllArgsConstructor
    public enum Type implements BasicEnums {
        COMMON(1, "通用"),
        EXCLUSIVE(2, "商品特有"),
        ;
        private final Integer value;
        private final String text;

        public static Type getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
