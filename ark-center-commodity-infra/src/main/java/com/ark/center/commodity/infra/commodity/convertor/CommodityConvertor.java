package com.ark.center.commodity.infra.commodity.convertor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.dto.SkuAttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.spu.Sku;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuSales;
import com.ark.center.commodity.domain.spu.aggregate.Commodity;
import com.ark.center.commodity.domain.spu.vo.Attr;
import com.ark.center.commodity.domain.spu.vo.Picture;
import com.ark.center.commodity.domain.spu.vo.SalesInfo;
import com.ark.component.web.util.bean.BeanConvertor;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommodityConvertor {

    public default Spu convertToSpuDO(Commodity commodity) {
        Spu spu = BeanConvertor.copy(commodity, Spu.class);
        spu.setShelfStatus(commodity.getShelfStatus().getValue());
        spu.setVerifyStatus(commodity.getVerifyStatus().getValue());
        return spu;
    }

    CommodityPageDTO toCommodityPageDTO(Spu spu);

    List<CommodityPageDTO> toCommodityPageDTO(List<Spu> spuList);

    public default Sku convertToSkuDO(Spu spu, Picture picture, com.ark.center.commodity.domain.spu.vo.Sku sku) {
        Sku skuDO = new Sku();
        if (sku.getId() != null && sku.getId() > 0) {
            skuDO.setId(sku.getId());
        }
        skuDO.setSpuName(spu.getName());
        skuDO.setSpuId(spu.getId());
        skuDO.setCode(sku.getCode());
        skuDO.setSalesPrice(sku.getSalesPrice());
        skuDO.setCostPrice(sku.getCostPrice());
        skuDO.setStock(sku.getStock());
        skuDO.setWarnStock(sku.getWarnStock());
        skuDO.setSpecData(JSONObject.toJSONString(sku.getSpecList()));
        // todo 暂时用spu的主图设置，后面再增加sku图片的功能
        skuDO.setMainPicture(picture.getUrl());
        return skuDO;
    }

    public default SalesInfo toSpuSales(SpuSales spuSales) {
        return new SalesInfo(spuSales.getSpuId(),
                spuSales.getFreightTemplateId(),
                spuSales.getPcDetailHtml(),
                spuSales.getMobileDetailHtml(),
                JSON.parseArray(spuSales.getParamData(), Attr.class));
    }

    public static List<com.ark.center.commodity.domain.spu.vo.Sku> convertToSku(List<Sku> doList) {
        return doList.stream().map(sku -> {
            List<Attr> specList = JSON.parseArray(sku.getSpecData(),
                    Attr.class);
            return new com.ark.center.commodity.domain.spu.vo.Sku(sku.getId(),
                    sku.getCode(),
                    "",
                    sku.getSalesPrice(),
                    sku.getCostPrice(),
                    sku.getStock(),
                    sku.getWarnStock(),
                    "",
                    specList);
        }).collect(Collectors.toList());
    }

    public default Commodity toAggregate(Spu dataObject) {
        Commodity commodity = BeanConvertor.copy(dataObject, Commodity.class);
        commodity.setShelfStatus(Commodity.ShelfStatus.getByValue(dataObject.getShelfStatus()));
        commodity.setVerifyStatus(Commodity.VerifyStatus.getByValue(dataObject.getVerifyStatus()));
        return commodity;
    }

    public static List<SkuDTO> convertToSkuDTO(List<Sku> skuList) {
        List<SkuDTO> skuDTOList = Lists.newArrayListWithCapacity(skuList.size());
        for (Sku sku : skuList) {
            SkuDTO skuRespDTO = new SkuDTO();
            skuRespDTO.setId(sku.getId());
            skuRespDTO.setSpuName(sku.getSpuName());
            skuRespDTO.setCode(sku.getCode());
            skuRespDTO.setSalesPrice(sku.getSalesPrice());
            skuRespDTO.setCostPrice(sku.getCostPrice());
            skuRespDTO.setStock(sku.getStock());
            skuRespDTO.setWarnStock(sku.getWarnStock());
            skuRespDTO.setSpecList(JSON.parseArray(sku.getSpecData(), SkuAttrDTO.class));
            skuRespDTO.setMainPicture(sku.getMainPicture());
            skuDTOList.add(skuRespDTO);
        }
        return skuDTOList;
    }
}
