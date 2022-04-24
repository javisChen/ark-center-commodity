package com.kt.cloud.commodity.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kt.component.orm.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * sku
 * </p>
 *
 * @author EOP
 * @since 2022-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("co_sku")
public class SkuDO extends BaseEntity {


    /**
     * SpuId，关联co_spu.id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * spu编码
     */
    @TableField("code")
    private String code;

    /**
     * 销售价（单位：分）
     */
    @TableField("sales_price")
    private Integer salesPrice;

    /**
     * 成本价（单位：分）
     */
    @TableField("cost_price")
    private Integer costPrice;

    /**
     * 库存
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 预警库存
     */
    @TableField("warn_stock")
    private Integer warnStock;

    /**
     * 销售参数JSON
     */
    @TableField("spec_data")
    private String specData;

}
