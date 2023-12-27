package com.ark.center.product;

import com.ark.center.product.infra.product.gateway.es.CommodityRepository;
import com.ark.component.cache.redis.RedisCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
public class StockDeductLuaTest {

    @Autowired
    private RedisCacheService cacheService;

    @Autowired
    private CommodityRepository commodityRepository;

    @Test
    public void test() {
    }
}
