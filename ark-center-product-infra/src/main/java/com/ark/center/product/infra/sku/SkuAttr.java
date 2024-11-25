package com.ark.center.product.infra.sku;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * sku规格属性
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sku_attr")
public class SkuAttr extends BaseEntity {

    /**
     * skuId，关联sku.id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 属性id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 属性名称
     */
    @TableField("attr_name")
    private String attrName;

    /**
     * 属性值
     */
    @TableField("attr_value")
    private String attrValue;

}
