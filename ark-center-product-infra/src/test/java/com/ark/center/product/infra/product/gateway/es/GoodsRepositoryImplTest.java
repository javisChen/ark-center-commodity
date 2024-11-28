package com.ark.center.product.infra.product.gateway.es;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.ark.center.product.client.search.query.SearchQuery;
import com.ark.center.product.infra.product.es.GoodsRepositoryImpl;
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
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setKeyword("米");
        searchQuery.setBrand("1^2^3^4");
        searchQuery.setCategory(1L);
        String input = "8_||蓝^9_32G^10_256";
        searchQuery.setAttrs(input);
        NativeQuery query = goodsRepository.buildNativeQueryBuilder(searchQuery);
        System.out.println(query.getQuery().toString());
    }
}