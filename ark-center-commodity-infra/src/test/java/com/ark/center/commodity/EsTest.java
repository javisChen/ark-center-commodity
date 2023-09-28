package com.ark.center.commodity;

import com.ark.center.commodity.infra.commodity.gateway.es.CommodityDoc;
import com.ark.center.commodity.infra.commodity.gateway.es.CommodityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
public class EsTest {

    @Autowired
    private CommodityRepository commodityRepository;

    @Test
    public void test() {
        Iterable<CommodityDoc> all = commodityRepository.findAll();
        System.out.println(all);
    }
}
