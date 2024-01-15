package com.ark.center.product.infra.product.gateway.es;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.ark.center.product.client.search.query.SearchQry;
import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

import java.util.List;

class GoodsRepositoryImplTest {

    GoodsRepositoryImpl goodsRepository = new GoodsRepositoryImpl(null);

    @Test
    void buildAttrQuery() {
        String input = "8_红||蓝^9_32G^10_256";
        List<Query> query = goodsRepository.buildAttrQuery(input);
        for (Query nestedQuery : query) {
            System.out.println(nestedQuery);
        }
    }

    @Test
    void buildNativeQueryBuilder() {
        SearchQry searchQry = new SearchQry();
        searchQry.setKeyword("米");
        searchQry.setBrand("1^2^3^4");
        searchQry.setCategory(1L);
        String input = "8_||蓝^9_32G^10_256";
        searchQry.setAttrs(input);
        NativeQuery query = goodsRepository.buildNativeQueryBuilder(searchQry);
        System.out.println(query.getQuery().toString());
    }
}