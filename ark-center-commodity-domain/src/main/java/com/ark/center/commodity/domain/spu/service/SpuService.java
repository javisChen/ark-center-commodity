package com.ark.center.commodity.domain.spu.service;

import com.ark.center.commodity.client.attr.dto.AttrOptionDTO;
import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.domain.attr.repository.AttrGateway;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.component.common.util.assemble.FieldsAssembler;
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
        List<AttrDTO> attrDTOS = spuGateway.selectSpecs(spuId);
        FieldsAssembler.execute(attrDTOS,
                AttrDTO::getAttrId,
                AttrDTO::setOptionList,
                attrGateway::selectOptions,
                AttrOptionDTO::getAttrId);
        return attrDTOS;
    }
}
