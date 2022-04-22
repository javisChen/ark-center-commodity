package com.kt.cloud.commodity.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.orm.mybatis.base.BaseEntity;
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
@TableName("co_sku_attr")
public class SkuAttrDO extends BaseEntity {

    /**
     * skuId，关联co_sku.id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 属性值
     */
    @TableField("attr_value")
    private String attrValue;

}
