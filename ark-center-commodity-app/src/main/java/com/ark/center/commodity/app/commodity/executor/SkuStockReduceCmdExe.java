package com.ark.center.commodity.app.commodity.executor;

import com.ark.center.commodity.client.commodity.command.SkuStockReduceCmd;
import com.ark.center.commodity.infra.commodity.gateway.cache.CommodityCacheConst;
import com.ark.component.cache.CacheService;
import com.ark.component.exception.ExceptionFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class SkuStockReduceCmdExe {

    private final CacheService cacheService;

    public void execute(List<SkuStockReduceCmd> cmdList) {
        for (SkuStockReduceCmd cmd : cmdList) {
            Long quantity = Long.valueOf(cmd.getQuantity());
            Long remain = cacheService.decrement(CommodityCacheConst.REDIS_SKU_STOCK + cmd.getId(), quantity);
            if (remain < 0) {
                cacheService.increment(CommodityCacheConst.REDIS_SKU_STOCK + cmd.getId(), quantity);
                throw ExceptionFactory.userException("商品库存不足！");
            }
        }
    }
}
