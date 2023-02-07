package com.ark.center.commodity.infra.commodity.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ark.center.commodity.client.commodity.dto.SkuAttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.vo.Attr;
import com.ark.center.commodity.domain.commodity.vo.Picture;
import com.ark.center.commodity.domain.commodity.vo.SalesInfo;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import com.ark.center.commodity.infra.commodity.repository.db.SkuDO;
import com.ark.center.commodity.infra.commodity.repository.db.SpuDO;
import com.ark.center.commodity.infra.commodity.repository.db.SpuSalesDO;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.RepositoryConvertor;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommodityConvertor extends RepositoryConvertor<Commodity, SpuDO> {

    public SpuDO convertToSpuDO(Commodity commodity) {
        SpuDO spuDO = BeanConvertor.copy(commodity, SpuDO.class);
        spuDO.setShelfStatus(commodity.getShelfStatus().getValue());
        spuDO.setVerifyStatus(commodity.getVerifyStatus().getValue());
        return spuDO;
    }

    public SkuDO convertToSkuDO(SpuDO spuDO, Picture picture, Sku sku) {
        SkuDO skuDO = new SkuDO();
        if (sku.getId() != null && sku.getId() > 0) {
            skuDO.setId(sku.getId());
        }
        skuDO.setSpuName(spuDO.getName());
        skuDO.setSpuId(spuDO.getId());
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

    public SalesInfo toSpuSales(SpuSalesDO spuSalesDO) {
        return new SalesInfo(spuSalesDO.getSpuId(),
                spuSalesDO.getFreightTemplateId(),
                spuSalesDO.getPcDetailHtml(),
                spuSalesDO.getMobileDetailHtml(),
                JSON.parseArray(spuSalesDO.getParamData(), Attr.class));
    }

    public static List<Sku> convertToSku(List<SkuDO> doList) {
        return doList.stream().map(skuDO -> {
            List<Attr> specList = JSON.parseArray(skuDO.getSpecData(),
                    Attr.class);
            return new Sku(skuDO.getId(),
                    skuDO.getCode(),
                    "",
                    skuDO.getSalesPrice(),
                    skuDO.getCostPrice(),
                    skuDO.getStock(),
                    skuDO.getWarnStock(),
                    "",
                    specList);
        }).collect(Collectors.toList());
    }

    @Override
    public Commodity toAggregate(SpuDO dataObject) {
        Commodity commodity = BeanConvertor.copy(dataObject, Commodity.class);
        commodity.setShelfStatus(Commodity.ShelfStatus.getByValue(dataObject.getShelfStatus()));
        commodity.setVerifyStatus(Commodity.VerifyStatus.getByValue(dataObject.getVerifyStatus()));
        return commodity;
    }

    public static List<SkuDTO> convertToSkuDTO(List<SkuDO> skuList) {
        List<SkuDTO> skuDTOList = Lists.newArrayListWithCapacity(skuList.size());
        for (SkuDO skuDO : skuList) {
            SkuDTO skuRespDTO = new SkuDTO();
            skuRespDTO.setId(skuDO.getId());
            skuRespDTO.setSpuName(skuDO.getSpuName());
            skuRespDTO.setCode(skuDO.getCode());
            skuRespDTO.setSalesPrice(skuDO.getSalesPrice());
            skuRespDTO.setCostPrice(skuDO.getCostPrice());
            skuRespDTO.setStock(skuDO.getStock());
            skuRespDTO.setWarnStock(skuDO.getWarnStock());
            skuRespDTO.setSpecList(JSON.parseArray(skuDO.getSpecData(), SkuAttrDTO.class));
            skuRespDTO.setMainPicture(skuDO.getMainPicture());
            skuDTOList.add(skuRespDTO);
        }
        return skuDTOList;
    }
}
