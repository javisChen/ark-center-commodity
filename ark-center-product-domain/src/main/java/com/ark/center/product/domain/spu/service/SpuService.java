package com.ark.center.product.domain.spu.service;

import com.ark.center.product.client.attr.dto.AttrOptionDTO;
import com.ark.center.product.client.goods.dto.AttrDTO;
import com.ark.center.product.domain.attr.repository.AttrGateway;
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
    public List<AttrDTO> querySpecs(List<Long> spuIds) {
        List<AttrDTO> attrDTOS = spuGateway.selectSpecs(spuIds);
        DataProcessor.create(attrDTOS)
                .keySelect(AttrDTO::getAttrId)
                .query(attrGateway::selectOptions)
                .keyBy(AttrOptionDTO::getAttrId)
                .collection()
                .process(AttrDTO::setOptionList);
        return attrDTOS;
    }
}
