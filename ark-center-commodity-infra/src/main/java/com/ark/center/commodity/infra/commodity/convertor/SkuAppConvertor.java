package com.ark.center.commodity.infra.commodity.convertor;

import com.ark.center.commodity.client.commodity.dto.SkuAttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.commodity.vo.Attr;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkuAppConvertor {

    List<SkuDTO> convertSkuToDTO(List<Sku> skus);

    List<SkuAttrDTO> convertAttrToDTO(List<Attr> skus);
//
//    private static SkuDTO convertSkuToDTO(Sku sku) {
//        SkuDTO skuDTO = new SkuDTO();
//        skuDTO.setId(sku.getId());
//        skuDTO.setCode(sku.getCode());
//        skuDTO.setSpuName(sku.getSpuName());
//        skuDTO.setSalesPrice(sku.getSalesPrice());
//        skuDTO.setCostPrice(sku.getCostPrice());
//        skuDTO.setStock(sku.getStock());
//        skuDTO.setWarnStock(sku.getWarnStock());
//        skuDTO.setMainPicture(sku.getMainPicture());
//        skuDTO.setSpecList(convertAttrToDTO(sku.getSpecList()));
//        return skuDTO;
//    }
//
//    private static List<SkuAttrDTO> convertAttrToDTO(List<Attr> attrs) {
//        return attrs.stream().map(SkuAppConvertor::convertAttrToDTO).collect(Collectors.toList());
//    }
//
//    private static SkuAttrDTO convertAttrToDTO(Attr attr) {
//        SkuAttrDTO skuAttrDTO = new SkuAttrDTO();
//        skuAttrDTO.setAttrId(attr.getAttrId());
//        skuAttrDTO.setAttrName(attr.getAttrName());
//        skuAttrDTO.setAttrValue(attr.getAttrValue());
//        return skuAttrDTO;
//    }
}
