package com.ark.center.product.domain.spu.service;

import com.ark.center.product.client.attr.dto.AttrOptionDTO;
import com.ark.center.product.client.goods.dto.AttrDTO;
import com.ark.center.product.domain.attr.repository.AttrGateway;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.component.common.util.assemble.FieldsAssembler;
import com.google.common.collect.Lists;
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
    public List<AttrDTO> querySpecs(Long spuId) {
        return querySpecs(Lists.newArrayList(spuId));
    }

    /**
     * 查询Spu规格
     */
    public List<AttrDTO> querySpecs(List<Long> spuIds) {
        List<AttrDTO> attrDTOS = spuGateway.selectSpecs(spuIds);
        FieldsAssembler.execute(attrDTOS,
                AttrDTO::getAttrId,
                attrGateway::selectOptions,
                AttrDTO::setOptionList,
                AttrOptionDTO::getAttrId);
        return attrDTOS;
    }
}
