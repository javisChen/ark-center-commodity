package com.ark.center.commodity.domain.spu.gateway;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.spu.Sku;
import com.ark.center.commodity.domain.spu.SkuAttr;
import com.ark.ddd.base.Repository;

import java.util.List;

public interface SkuGateway {

    List<Sku> queryByIds(List<Long> ids);

    List<SkuDTO> selectBySpuId(Long spuId);

    void update(Sku sku);

    void insert(Sku sku);

    void saveAttrs(List<SkuAttr> attrs);

    void deleteBySpuId(Long spuId);

    void deleteAttrsBySpuId(Long spuId);
}
