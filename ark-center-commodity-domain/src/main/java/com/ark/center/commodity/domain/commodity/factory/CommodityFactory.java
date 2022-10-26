package com.ark.center.commodity.domain.commodity.factory;
import com.ark.center.commodity.client.commodity.command.SkuUpdateCmd;
import com.google.common.collect.Lists;
import com.ark.center.commodity.domain.commodity.vo.SaleInfo;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity.ShelfStatus;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity.VerifyStatus;

import com.ark.center.commodity.client.commodity.command.CommoditySaveCmd;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.assembler.CommodityAssembler;
import com.ark.center.commodity.domain.commodity.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommodityFactory {

    private final CommodityAssembler commodityAssembler;

    private final CommodityRepository commodityRepository;

    public Commodity create(CommoditySaveCmd cmd) {
        Commodity commodity = new Commodity();
        commodity.setName(cmd.getName());
        commodity.setCode(cmd.getCode());
        commodity.setDescription(cmd.getDescription());
        commodity.setMainPicture(cmd.getMainPicture());
        commodity.setShelfStatus(ShelfStatus.getByValue(cmd.getShelfStatus()));
        commodity.setVerifyStatus(VerifyStatus.NO_CHECK);
        commodity.setSales(0);
        commodity.setShowPrice(cmd.getShowPrice());
        commodity.setUnit(cmd.getUnit());
        commodity.setWeight(cmd.getWeight());
        commodity.setBrandId(cmd.getBrandId());
        commodity.setCategoryId(cmd.getCategoryId());
        List<SkuUpdateCmd> skuList = cmd.getSkuList();
        for (SkuUpdateCmd skuUpdateCmd : skuList) {

        }
        commodity.setSkuList(skuList);
        SaleInfo saleInfo = new SaleInfo();
        commodity.setSaleInfo(saleInfo);
        commodity.setPictures(Lists.newArrayList());


        return null;
    }


}
