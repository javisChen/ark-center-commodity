package com.ark.center.product.domain.spu.service;

import com.ark.center.product.client.attr.dto.AttrOptionDTO;
import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.domain.attr.AttrOption;
import com.ark.center.product.domain.attr.gateway.AttrGateway;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.component.common.util.assemble.DataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpuService {

    private final SpuGateway spuGateway;
    private final AttrGateway attrGateway;

    /**
     * 查询Spu规格
     */
    public List<GoodsAttrDTO> querySpecWithOptions(List<Long> spuIds, AttrOption.Type optionType) {
        List<GoodsAttrDTO> goodsAttrDTOS = spuGateway.selectSpecs(spuIds);
        DataProcessor.create(goodsAttrDTOS)
                .keySelect(GoodsAttrDTO::getAttrId)
                .query(attrs -> attrGateway.selectOptions(attrs, spuIds, optionType))
                .keyBy(AttrOptionDTO::getAttrId)
                .collection()
                .process(GoodsAttrDTO::setOptionList);
        return goodsAttrDTOS;
    }
}
