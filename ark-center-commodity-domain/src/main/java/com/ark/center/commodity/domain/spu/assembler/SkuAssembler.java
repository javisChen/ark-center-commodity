package com.ark.center.commodity.domain.spu.assembler;

import com.alibaba.fastjson2.JSON;
import com.ark.center.commodity.client.commodity.command.SkuCmd;
import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.spu.Sku;
import com.ark.center.commodity.domain.spu.Spu;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuAssembler {

    List<SkuDTO> toSkuDTO(List<Sku> skuList);

    default SkuDTO toSkuDTO(Sku sku) {
        SkuDTO skuDTO = new SkuDTO();
        skuDTO.setId(sku.getId());
        skuDTO.setCode(sku.getCode());
        skuDTO.setSpuName(sku.getSpuName());
        skuDTO.setSalesPrice(sku.getSalesPrice());
        skuDTO.setCostPrice(sku.getCostPrice());
        skuDTO.setStock(sku.getStock());
        skuDTO.setWarnStock(sku.getWarnStock());
        skuDTO.setMainPicture(sku.getMainPicture());
        skuDTO.setSpecList(JSON.parseArray(sku.getSpecData(), AttrDTO.class));
        return skuDTO;
    }

    default Sku toSku(Spu spu, SkuCmd skuCmd, String mainPic) {
        Sku sku = new Sku();
        sku.setId(skuCmd.getId());
        sku.setSpuId(spu.getId());
        sku.setSpuName(spu.getName());
        sku.setCode(skuCmd.getCode());
        sku.setMainPicture(mainPic);
        sku.setSalesPrice(skuCmd.getSalesPrice());
        sku.setCostPrice(skuCmd.getCostPrice());
        sku.setStock(skuCmd.getStock());
        sku.setWarnStock(skuCmd.getWarnStock());
        sku.setSpecData(sku.getSpecData());
        sku.setId(skuCmd.getId());
        return sku;
    }
}














