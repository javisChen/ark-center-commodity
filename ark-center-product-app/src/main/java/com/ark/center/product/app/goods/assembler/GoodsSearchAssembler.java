package com.ark.center.product.app.goods.assembler;

import com.ark.center.product.client.search.dto.SkuSearchDTO;
import com.ark.center.product.infra.product.gateway.es.SkuDoc;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GoodsSearchAssembler {

    List<SkuSearchDTO> toDTO(List<SkuDoc> searchHits);
}
