package com.ark.center.commodity.domain.attr.aggregate;

import com.ark.center.commodity.domain.attr.vo.AttrOption;
import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class Attr {

    private Long id;

    private String name;

    private InputType inputType;

    private Type type;

    private Integer sort;

    private Long attrTemplateId;

    private Long attrGroupId;

    private Boolean canManualAdd;

    private List<AttrOption> options;

    private LocalDateTime gmtCreate;

    public boolean isSelectInputType() {
        return inputType.equals(InputType.SELECT);
    }
    public boolean isInputType() {
        return inputType.equals(InputType.INPUT);
    }

    public void removeOptions() {
        if (CollectionUtils.isNotEmpty(options)) {
            options.clear();
        }
    }

    @Getter
    @AllArgsConstructor
    public enum InputType implements BasicEnums {
        INPUT(1, "手工录入"),
        SELECT(2, "从选项列表选取"),
        ;
        private final Integer value;
        private final String text;

        public static InputType getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Type implements BasicEnums {
        SPEC(1, "规格"),
        PARAM(2, "参数"),
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

    @Getter
    @AllArgsConstructor
    public enum CanManualAdd implements BasicEnums {
        NO(0, "不支持"),
        YES(1, "支持"),
        ;
        private final Integer value;
        private final String text;

        public static CanManualAdd getByValue(Integer value) {
            return EnumUtils.getByValue(values(), value);
        }

        public static String getText(Integer value) {
            return EnumUtils.getTextByValue(values(), value);
        }
    }
}
