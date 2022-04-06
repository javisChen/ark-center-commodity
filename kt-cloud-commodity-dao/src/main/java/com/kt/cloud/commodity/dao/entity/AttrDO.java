package com.kt.cloud.commodity.dao.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kt.component.common.enums.BasicEnums;
import com.kt.component.common.enums.EnumUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_attr")
public class AttrDO extends BaseEntity {


    /**
     * 属性名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]
     */
    @TableField("input_type")
    private Integer inputType;

    /**
     * 属性的类型，enums[SPEC,规格,1;PARAM,参数,2]
     */
    @TableField("`type`")
    private Integer type;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 模板ID，关联co_attr_template.id
     */
    @TableField("attr_template_id")
    private Long attrTemplateId;

    /**
     * 是否支持手动新增，enums[NO,不支持,0;YES,支持,1]
     */
    @TableField("can_manual_add")
    private Integer canManualAdd;

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
