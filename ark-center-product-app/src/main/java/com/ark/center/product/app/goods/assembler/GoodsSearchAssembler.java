package com.ark.center.product.app.goods.assembler;
import com.google.common.collect.Lists;

import com.ark.center.product.client.search.dto.SkuSearchDTO;
import com.ark.center.product.infra.product.gateway.es.SkuDoc;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GoodsSearchAssembler {

    default List<SkuSearchDTO> toDTO(SearchHits<SkuDoc> searchHits) {
        List<SkuSearchDTO> records = Lists.newArrayListWithCapacity(searchHits.getSearchHits().size());
        for (SearchHit<SkuDoc> searchHit : searchHits) {
            List<String> highlights = searchHit.getHighlightField("skuName");
            SkuDoc skuDoc = searchHit.getContent();
            if (CollectionUtils.isNotEmpty(highlights)) {
                skuDoc.setSkuName(highlights.getFirst());
            }
            records.add(toDTO(skuDoc));
        }
        return records;
    }

    default SkuSearchDTO toDTO(SkuDoc skuDoc) {
        SkuSearchDTO skuSearchDTO = new SkuSearchDTO();
        skuSearchDTO.setSkuId(skuDoc.getSkuId());
        skuSearchDTO.setSpuId(skuDoc.getSpuId());
        skuSearchDTO.setSkuName(skuDoc.getSkuName());
        skuSearchDTO.setBrandId(skuDoc.getBrandId());
        skuSearchDTO.setCategoryId(skuDoc.getCategoryId());
        skuSearchDTO.setSalesPrice(skuDoc.getSalesPrice());
        skuSearchDTO.setPictures(skuDoc.getPictures());
        return skuSearchDTO;
    }
}
