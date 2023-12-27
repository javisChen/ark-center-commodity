package com.ark.center.product.domain.inventory.gateway;

import com.ark.center.product.domain.inventory.Inventory;

import java.util.List;

public interface InventoryGateway {

    void insert(List<Inventory> inventories);

    List<Inventory> selectBySpuId(Long spuId);

    void delete(List<Long> ids);

    void deleteRecords(List<Long> ids);

    List<Inventory> selectBySkuIds(List<Long> skuIds);

    void updateAvailableStock(Inventory inventory);
}
