package com.ark.center.product.domain.sku;

import com.ark.center.product.domain.sku.typehandler.SkuAttrTypeHandler;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
@TableName(value = "sku", autoResultMap = true)
public class Sku extends BaseEntity {


    /**
     * SpuId，关联spu.id
     */
    @TableField("spu_id")
    private Long spuId;


    /**
     * sku名称
     */
    @TableField("name")
    private String name;

    /**
     * spu编码
     */
    @TableField("code")
    private String code;

    /**
     * sku主图
     */
    @TableField("main_picture")
    private String mainPicture;

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
     * 规格属性JSON
     */
    @TableField(value = "specs", typeHandler = SkuAttrTypeHandler.class)
    private List<SkuAttr> specs;

}
