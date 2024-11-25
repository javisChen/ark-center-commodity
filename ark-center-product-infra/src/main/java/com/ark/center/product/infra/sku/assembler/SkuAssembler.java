package com.ark.center.product.infra.sku.assembler;

import com.ark.center.product.client.goods.command.AttrCmd;
import com.ark.center.product.client.goods.command.SkuCmd;
import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.infra.sku.Sku;
import com.ark.center.product.infra.sku.SkuAttr;
import com.ark.center.product.infra.spu.Spu;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuAssembler {

    List<SkuDTO> toSkuDTO(List<Sku> skuList);

    default SkuDTO toSkuDTO(Sku sku) {
        SkuDTO skuDTO = new SkuDTO();
        skuDTO.setId(sku.getId());
        skuDTO.setSpuId(sku.getSpuId());
        skuDTO.setCode(sku.getCode());
        skuDTO.setName(sku.getName());
        skuDTO.setSalesPrice(sku.getSalesPrice());
        skuDTO.setCostPrice(sku.getCostPrice());
        skuDTO.setStock(sku.getStock());
        skuDTO.setWarnStock(sku.getWarnStock());
        skuDTO.setMainPicture(sku.getMainPicture());
        skuDTO.setSpecs(toGoodsAttr(sku.getSpecs()));
        return skuDTO;
    }

    List<GoodsAttrDTO> toGoodsAttr(List<SkuAttr> specs);

    default Sku toSku(Spu spu, SkuCmd skuCmd, String mainPic) {
        List<AttrCmd> specList = skuCmd.getSpecs();
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        sku.setId(skuCmd.getId());
        sku.setName(buildSkuName(spu.getName(), specList));
        sku.setCode(skuCmd.getCode());
        sku.setMainPicture(mainPic);
        sku.setSalesPrice(skuCmd.getSalesPrice());
        sku.setCostPrice(skuCmd.getCostPrice());
        sku.setStock(skuCmd.getStock());
        sku.setWarnStock(skuCmd.getWarnStock());
        sku.setSpecs(toSpecs(skuCmd.getSpecs()));
        sku.setId(skuCmd.getId());
        return sku;
    }

    List<SkuAttr> toSpecs(List<AttrCmd> specs);

    private String buildSkuName(String spuName, List<AttrCmd> specList) {
        return spuName + " " + specList.stream().map(AttrCmd::getAttrValue)
                .collect(Collectors.joining(" "));
    }
}














