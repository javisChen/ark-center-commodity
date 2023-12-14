package com.ark.center.product;

import com.ark.center.product.infra.product.gateway.es.GoodsDoc;
import com.ark.center.product.infra.product.gateway.es.CommodityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
public class EsTest {

    @Autowired
    private CommodityRepository commodityRepository;

    @Test
    public void test() {
        Iterable<GoodsDoc> all = commodityRepository.findAll();
        System.out.println(all);
    }
}
