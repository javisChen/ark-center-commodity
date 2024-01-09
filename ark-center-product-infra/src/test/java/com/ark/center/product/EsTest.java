package com.ark.center.product;

import com.ark.center.product.infra.product.gateway.es.GoodsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
public class EsTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void test() {
    }
}
