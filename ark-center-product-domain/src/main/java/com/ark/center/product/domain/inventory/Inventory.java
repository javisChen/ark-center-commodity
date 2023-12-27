package com.ark.center.product.domain.inventory;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存领域模型
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("inventory")
public class Inventory extends BaseEntity {

    /**
     * spu id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * sku id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 可用的库存数
     */
    @TableField("available_stock")
    private Integer availableStock;

    /**
     * 已锁定的库存数
     */
    @TableField("locked_stock")
    private Integer lockedStock;

    /**
     * 已售库存
     */
    @TableField("sold_stock")
    private Integer soldStock;

}
