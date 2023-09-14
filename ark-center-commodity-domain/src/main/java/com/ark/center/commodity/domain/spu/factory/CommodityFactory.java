package com.ark.center.commodity.domain.spu.factory;

import com.ark.center.commodity.domain.spu.assembler.SpuAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommodityFactory {
    private final SpuAssembler spuAssembler;
//    public Commodity create(CommodityCreateCmd cmd) {
//        Commodity commodity = spuAssembler.cmdToCommodity(cmd);
//        commodity.setShelfStatus(ShelfStatus.getByValue(cmd.getShelfStatus()));
//        commodity.setVerifyStatus(VerifyStatus.NO_CHECK);
//
//        List<Sku> skus = cmd.getSkuList()
//                .stream()
//                .map(spuAssembler::toSku)
//                .collect(Collectors.toList());
//        commodity.setSkuList(skus);
//
//        commodity.setSalesInfo(spuAssembler.toSalesInfo(cmd));
//
//        commodity.setPicList(spuAssembler.toPicture(cmd));
//
//        commodity.setAttrOptionList(spuAssembler.toAttrOption(cmd));
//
//        return commodity;
//    }


}
