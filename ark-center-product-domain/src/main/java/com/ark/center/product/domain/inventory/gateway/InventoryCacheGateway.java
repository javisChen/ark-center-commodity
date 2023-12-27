package com.ark.center.product.domain.inventory.gateway;

import com.ark.center.product.domain.inventory.Inventory;

import java.util.List;

public interface InventoryCacheGateway {

    void saveStock(List<Inventory> inventories);

    Long decrAvailableStock(Long skuId, Long quantity);

    Long incrAvailableStock(Long skuId, Long quantity);

    void delStock(List<Inventory> inventories);

    void updateAvailableStock(List<Inventory> inventories);
}
