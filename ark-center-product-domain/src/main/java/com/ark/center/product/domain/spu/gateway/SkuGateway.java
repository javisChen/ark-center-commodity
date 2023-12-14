package com.ark.center.product.domain.spu.gateway;

import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.spu.Sku;
import com.ark.center.product.domain.spu.SkuAttr;

import java.util.List;

public interface SkuGateway {

    List<Sku> queryByIds(List<Long> ids);

    List<SkuDTO> selectBySpuId(Long spuId);

    List<SkuDTO> selectBySpuIds(List<Long> spuIds);

    void update(Sku sku);

    void insert(Sku sku);

    void saveAttrs(List<SkuAttr> attrs);

    void deleteBySpuId(Long spuId);

    void deleteAttrsBySpuId(Long spuId);
}
