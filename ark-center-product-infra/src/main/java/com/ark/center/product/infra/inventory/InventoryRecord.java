package com.ark.center.product.infra.inventory;

import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存操作记录
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pro_inventory_record")
@Builder
public class InventoryRecord extends BaseEntity {

    /**
     * 订单id
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 库存id
     */
    @TableField("inventory_id")
    private Long inventoryId;

    /**
     * 操作的数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 预扣库存
     */
    @TableField("type")
    private Integer type;

}
