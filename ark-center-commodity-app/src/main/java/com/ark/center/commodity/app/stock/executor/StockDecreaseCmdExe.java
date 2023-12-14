package com.ark.center.commodity.app.stock.executor;

import com.ark.center.commodity.client.stock.command.StockDecreaseCmd;
import com.ark.center.commodity.client.stock.dto.StockDecreaseDTO;
import com.ark.center.commodity.infra.commodity.gateway.cache.CommodityCacheConst;
import com.ark.component.cache.CacheService;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class StockDecreaseCmdExe {

    private final CacheService cacheService;

    /**
     * todo 需要优化，目前是比较简单的实现
     */
    public StockDecreaseDTO execute(StockDecreaseCmd cmd) {
        log.info("执行库存扣减 = {}", cmd.getItems());
        StockDecreaseDTO decreaseDTO = new StockDecreaseDTO();
        for (StockDecreaseCmd.Item item : cmd.getItems()) {
            Long quantity = Long.valueOf(item.getQuantity());
            Long skuId = item.getSkuId();
            Long remain = cacheService.decrement(CommodityCacheConst.REDIS_SKU_STOCK + skuId, quantity);
            if (remain < 0) {
                cacheService.increment(CommodityCacheConst.REDIS_SKU_STOCK + skuId, quantity);
                decreaseDTO.setResult(false);
                decreaseDTO.setSkuIds(Lists.newArrayList(skuId));
                // throw ExceptionFactory.userException("商品库存不足！");
            }
        }
        log.info("执行库存扣减成功...");
        return decreaseDTO;
    }
}
