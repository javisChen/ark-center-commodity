package com.ark.center.product.infra.product.gateway.es;

import com.ark.center.product.client.search.query.SearchQry;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface GoodsRepository {

    void saveAll(Iterable<SkuDoc> docs);
    void save(SkuDoc doc);

    void deleteById(Long id);

    void deleteAllById(List<Long> list);

    SearchHits<SkuDoc> search(SearchQry searchQry);
}
