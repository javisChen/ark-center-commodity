package com.ark.center.commodity.domain.commodity.factory;

import com.ark.center.commodity.client.commodity.command.CommoditySaveCmd;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity.ShelfStatus;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity.VerifyStatus;
import com.ark.center.commodity.domain.commodity.assembler.CommodityAssembler;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommodityFactory {
    private final CommodityAssembler commodityAssembler;
    public Commodity create(CommoditySaveCmd cmd) {
        Commodity commodity = commodityAssembler.cmdToCommodity(cmd);
        commodity.setShelfStatus(ShelfStatus.getByValue(cmd.getShelfStatus()));
        commodity.setVerifyStatus(VerifyStatus.NO_CHECK);

        List<Sku> skus = cmd.getSkuList()
                .stream()
                .map(commodityAssembler::toSku)
                .collect(Collectors.toList());
        commodity.setSkuList(skus);

        commodity.setSalesInfo(commodityAssembler.toSalesInfo(cmd));

        commodity.setPicList(commodityAssembler.toPicture(cmd));

        commodity.setAttrOptionList(commodityAssembler.toAttrOption(cmd));

        return commodity;
    }


}
