package com.ark.center.commodity.infrastructure.commodity.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.vo.Attr;
import com.ark.center.commodity.domain.commodity.vo.Picture;
import com.ark.center.commodity.domain.commodity.vo.SalesInfo;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SkuDO;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SpuDO;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SpuSalesDO;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.RepositoryConvertor;
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

    public SkuDO convertToSkuDO(Long spuId, Picture picture, Sku sku) {
        SkuDO skuDO = new SkuDO();
        if (sku.getId() != null && sku.getId() > 0) {
            skuDO.setId(sku.getId());
        }
        skuDO.setSpuId(spuId);
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
        SalesInfo salesInfo = new SalesInfo(spuSalesDO.getSpuId(),
                spuSalesDO.getFreightTemplateId(),
                spuSalesDO.getPcDetailHtml(),
                spuSalesDO.getMobileDetailHtml(),
                JSON.parseArray(spuSalesDO.getParamData(), Attr.class));
        return salesInfo;
    }

    public List<Sku> convertToSku(List<SkuDO> doList) {
        return doList.stream().map(skuDO -> {
            return new Sku(skuDO.getId(),
                    skuDO.getCode(),
                    skuDO.getSalesPrice(),
                    skuDO.getCostPrice(),
                    skuDO.getStock(),
                    skuDO.getWarnStock(),
                    JSON.parseArray(skuDO.getSpecData(),
                            Attr.class));
        }).collect(Collectors.toList());
    }

    @Override
    public Commodity toAggregate(SpuDO dataObject) {
        Commodity commodity = BeanConvertor.copy(dataObject, Commodity.class);
        commodity.setShelfStatus(Commodity.ShelfStatus.getByValue(dataObject.getShelfStatus()));
        commodity.setVerifyStatus(Commodity.VerifyStatus.getByValue(dataObject.getVerifyStatus()));
        return commodity;
    }
}
