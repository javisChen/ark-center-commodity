package com.ark.center.product.infra.inventory.gateway.cache;

import com.ark.center.product.Application;
import com.ark.center.product.domain.inventory.Inventory;
import com.ark.center.product.domain.inventory.gateway.InventoryCacheGateway;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = {Application.class})
public class InventoryCacheGatewayImplTest {

    private InventoryCacheGateway inventoryCacheGateway;

    @Test
    void cacheAvailableStock() {
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(getInventory(1L, 15, 20, 25));
        inventories.add(getInventory(2L, 10, 15, 20));
        inventoryCacheGateway.save(inventories);
    }

    private Inventory getInventory(long skuId, Integer available, Integer locked, Integer sold) {
        Inventory inventoryA = new Inventory();
        inventoryA.setSkuId(skuId);
        inventoryA.setAvailableStock(available);
        inventoryA.setLockedStock(locked);
        inventoryA.setSoldStock(sold);
        return inventoryA;
    }

    @Test
    void decrAvailableStock() {
    }

    @Test
    void incrAvailableStock() {
    }
}