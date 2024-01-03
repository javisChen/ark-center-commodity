package com.ark.center.product.infra.product.gateway.es;

import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<SkuDoc, Long> {
}
