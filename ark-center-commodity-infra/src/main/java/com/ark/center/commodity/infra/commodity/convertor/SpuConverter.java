package com.ark.center.commodity.infra.commodity.convertor;

import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.domain.spu.Spu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpuConverter {

    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "brandName", ignore = true)
    CommodityPageDTO toCommodityPageDTO(Spu spu);

}
