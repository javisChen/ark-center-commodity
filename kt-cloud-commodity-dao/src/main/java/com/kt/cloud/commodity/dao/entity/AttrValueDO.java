package com.kt.cloud.commodity.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.common.enums.BasicEnums;
import com.kt.component.common.enums.EnumUtils;
import com.kt.component.orm.mybatis.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>
 * 商品属性值
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_attr_value")
public class AttrValueDO extends BaseEntity {


    /**
     * 商品属性ID，关联co_attr.id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 选项值
     */
    @TableField("`value`")
    private String value;

    /**
     * 类型：enums[COMMON,通用,1;EXCLUSIVE,商品特有,2]
     */
    @TableField("`type`")
    private Integer type;

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
