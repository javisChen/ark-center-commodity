package com.ark.center.product.infra.product.convertor;

import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.sku.Sku;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuAppConvertor {

    List<SkuDTO> convertSkuToDTO(List<Sku> skus);

}
