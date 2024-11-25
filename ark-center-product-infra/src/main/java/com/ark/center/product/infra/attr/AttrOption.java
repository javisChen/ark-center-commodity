package com.ark.center.product.infra.attr;

import com.ark.component.common.enums.BasicEnums;
import com.ark.component.common.enums.EnumUtils;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>
 * 商品属性值选项
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("attr_option")
public class AttrOption extends BaseEntity {


    /**
     * 商品属性ID，关联attr.id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 关联spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 属性值内容
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
        EXCLUSIVE(2, "商品特有");

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
