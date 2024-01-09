package com.ark.center.product.infra.product.gateway.es;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.ark.center.product.client.search.query.SearchQry;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import static org.junit.jupiter.api.Assertions.*;

class GoodsRepositoryImplTest {

    GoodsRepositoryImpl goodsRepository = new GoodsRepositoryImpl(null);

    @Test
    void buildAttrQuery() {
        String input = "8_||蓝^9_32G^10_256";
        Query query = goodsRepository.buildAttrQuery(input);
        System.out.println(query.toString());
    }

    @Test
    void buildNativeQueryBuilder() {
        SearchQry searchQry = new SearchQry();
        searchQry.setKeyword("米");
        searchQry.setBrandIds("1^2^3^4");
        searchQry.setCategoryId(1L);
        String input = "8_||蓝^9_32G^10_256";
        searchQry.setSpecs(input);
        NativeQuery query = goodsRepository.buildNativeQueryBuilder(searchQry);
        System.out.println(query.getQuery().toString());
    }
}