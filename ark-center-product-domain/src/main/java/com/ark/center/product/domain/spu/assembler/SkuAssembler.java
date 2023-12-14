package com.ark.center.product.domain.spu.assembler;

import com.alibaba.fastjson2.JSON;
import com.ark.center.product.client.goods.command.AttrCmd;
import com.ark.center.product.client.goods.command.SkuCmd;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.spu.Sku;
import com.ark.center.product.domain.spu.Spu;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuAssembler {

    List<SkuDTO> toSkuDTO(List<Sku> skuList);

    default Sku toSku(Spu spu, SkuCmd skuCmd, String mainPic) {
        List<AttrCmd> specList = skuCmd.getSpecs();
        Sku sku = new Sku();
        sku.setId(skuCmd.getId());
        sku.setSpuId(spu.getId());
        String spuName = spu.getName();
        sku.setName(buildSkuName(spuName, specList));
        sku.setCode(skuCmd.getCode());
        sku.setMainPicture(mainPic);
        sku.setSalesPrice(skuCmd.getSalesPrice());
        sku.setCostPrice(skuCmd.getCostPrice());
        sku.setStock(skuCmd.getStock());
        sku.setWarnStock(skuCmd.getWarnStock());
        sku.setSpecData(JSON.toJSONString(specList));
        sku.setId(skuCmd.getId());
        return sku;
    }

    private String buildSkuName(String spuName, List<AttrCmd> specList) {
        return spuName + " " + specList.stream().map(AttrCmd::getAttrValue)
                .collect(Collectors.joining(" "));
    }
}














