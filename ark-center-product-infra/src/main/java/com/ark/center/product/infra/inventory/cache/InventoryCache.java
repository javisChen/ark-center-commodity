package com.ark.center.product.infra.inventory.cache;

import com.ark.center.product.infra.inventory.Inventory;
import com.ark.component.cache.redis.RedisCacheService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Days;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryCache implements InitializingBean {

    private final RedisCacheService redisCacheService;

    private final static String HASH_KEY_AVAILABLE_STOCK = "available";
    private final static String HASH_KEY_LOCKED_STOCK = "locked";
    private final static String HASH_KEY_SOLD_STOCK = "sold";

    /**
     * 库存缓存时效（默认7天）
     */
    private final int expires = Days.days(7).toStandardSeconds().getSeconds();

    private final LuaScriptManager luaScriptManager;

    public void afterPropertiesSet() {
    }


    public void save(List<Inventory> inventories) {

        List<Object> args = Lists.newArrayList(expires);
        List<String> keys = Lists.newArrayList();

        for (Inventory inventory : inventories) {
            String hashKey = buildStockCacheKey(inventory.getSkuId());
            keys.add(hashKey);
            args.add(HASH_KEY_AVAILABLE_STOCK);
            args.add(Objects.requireNonNullElse(inventory.getAvailableStock(), 0));
            args.add(HASH_KEY_LOCKED_STOCK);
            args.add(Objects.requireNonNullElse(inventory.getLockedStock(), 0));
            args.add(HASH_KEY_SOLD_STOCK);
            args.add(Objects.requireNonNullElse(inventory.getSoldStock(), 0));
        }
        RedisScript<Long> redisScript = luaScriptManager.find(LuaScriptManager.SCRIPT_SAVE_STOCK);
        redisCacheService.executeScript(redisScript, keys, args);
    }

    public void updateAvailableStock(List<Inventory> inventories) {

        List<Object> args = Lists.newArrayList(expires);
        List<String> keys = Lists.newArrayList();

        for (Inventory inventory : inventories) {
            String hashKey = buildStockCacheKey(inventory.getSkuId());
            keys.add(hashKey);
            args.add(HASH_KEY_AVAILABLE_STOCK);
            args.add(Objects.requireNonNullElse(inventory.getAvailableStock(), 0));
        }

        try {
            RedisScript<Long> redisScript = luaScriptManager.find(LuaScriptManager.SCRIPT_UPDATE_STOCK);
            redisCacheService.executeScript(redisScript, keys, args);
        } catch (Exception e) {
            log.error("Update available stock fail", e);
            throw e;
        }
    }

    public Long decrAvailableStock(Long skuId, Long quantity) {
        return redisCacheService.hIncrBy(buildStockCacheKey(skuId), HASH_KEY_AVAILABLE_STOCK, -quantity);
    }

    public Long incrAvailableStock(Long skuId, Long quantity) {
        return redisCacheService.hIncrBy(buildStockCacheKey(skuId), HASH_KEY_AVAILABLE_STOCK, quantity);
    }

    public void delStock(List<Inventory> inventories) {
        List<String> keys = inventories.stream().map(inventory -> buildStockCacheKey(inventory.getSkuId())).toList();
        redisCacheService.del(keys);
    }

    private String buildStockCacheKey(Long skuId) {
        return InventoryCacheKeys.SKU_STOCK + skuId;
    }
}
