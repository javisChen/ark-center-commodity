package com.ark.center.product.infra.product.gateway.es;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepository, InitializingBean {

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveAll(Iterable<SkuDoc> docs) {
        elasticsearchTemplate.save(docs);
    }

    @Override
    public void save(SkuDoc doc) {
        elasticsearchTemplate.save(doc);
    }

    @Override
    public void deleteById(Long id) {
        elasticsearchTemplate.delete(id.toString(), SkuDoc.class);
    }

    @Override
    public void deleteAllById(List<Long> list) {
        Query idsQuery = elasticsearchTemplate.idsQuery(list.stream().map(Object::toString).toList());
        ByQueryResponse delete = elasticsearchTemplate.delete(idsQuery, SkuDoc.class);
        System.out.println(delete);
    }

    @Override
    public Iterable<SkuDoc> findAll() {
        SearchHits<SkuDoc> searchHits = elasticsearchTemplate.search(Query.findAll(), SkuDoc.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IndexOperations indexOperations = elasticsearchTemplate.indexOps(SkuDoc.class);
        if (!indexOperations.exists()) {
            boolean b = indexOperations.createWithMapping();
            System.out.println("sku index created" + b);
        }
    }
}
