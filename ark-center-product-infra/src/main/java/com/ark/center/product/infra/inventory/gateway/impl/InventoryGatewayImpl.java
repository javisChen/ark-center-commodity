package com.ark.center.product.infra.inventory.gateway.impl;

import com.ark.center.product.domain.inventory.Inventory;
import com.ark.center.product.domain.inventory.InventoryRecord;
import com.ark.center.product.domain.inventory.gateway.InventoryGateway;
import com.ark.center.product.infra.inventory.gateway.db.InventoryMapper;
import com.ark.center.product.infra.inventory.gateway.db.InventoryRecordMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryGatewayImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryGateway {

    private final InventoryRecordMapper inventoryRecordMapper;


    @Override
    public void save(List<Inventory> inventories) {
        saveBatch(inventories);
    }

    @Override
    public List<Inventory> selectBySpuId(Long spuId) {
        return lambdaQuery()
                .select(Inventory::getSpuId,
                        Inventory::getSkuId,
                        Inventory::getAvailableStock,
                        Inventory::getSoldStock,
                        Inventory::getLockedStock
                )
                .eq(Inventory::getSpuId, spuId)
                .list();
    }

    @Override
    public void delete(List<Long> ids) {
        ids = ids.stream().sorted().toList();
        lambdaUpdate()
                .in(BaseEntity::getId, ids)
                .remove();
    }

    @Override
    public void deleteRecords(List<Long> ids) {
        ids = ids.stream().sorted().toList();
        LambdaUpdateWrapper<InventoryRecord> uw = Wrappers.lambdaUpdate(InventoryRecord.class)
                .in(InventoryRecord::getInventoryId, ids);
        inventoryRecordMapper.delete(uw);
    }

    @Override
    public List<Inventory> selectBySkuIds(List<Long> skuIds) {
        return lambdaQuery()
                .select(BaseEntity::getId,
                        Inventory::getSpuId,
                        Inventory::getSkuId,
                        Inventory::getAvailableStock,
                        Inventory::getSoldStock,
                        Inventory::getLockedStock
                )
                .in(Inventory::getSkuId, skuIds)
                .list();
    }

    @Override
    public void updateAvailableStock(Inventory inventory) {
        lambdaUpdate()
                .eq(Inventory::getSkuId, inventory.getSkuId())
                .set(Inventory::getAvailableStock, inventory);
    }
}
