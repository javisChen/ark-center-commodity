package com.ark.center.product.infra.product.gateway.es;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository {

    void saveAll(Iterable<SkuDoc> docs);
    void save(SkuDoc doc);

    void deleteById(Long id);

    void deleteAllById(List<Long> list);

    Iterable<SkuDoc> findAll();

}
