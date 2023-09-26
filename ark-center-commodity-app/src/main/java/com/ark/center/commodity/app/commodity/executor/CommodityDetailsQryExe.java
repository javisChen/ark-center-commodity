package com.ark.center.commodity.app.commodity.executor;

import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.attachment.Attachment;
import com.ark.center.commodity.domain.attachment.gateway.AttachmentGateway;
import com.ark.center.commodity.domain.category.gateway.CategoryGateway;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuAttr;
import com.ark.center.commodity.domain.spu.gateway.SkuGateway;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.center.commodity.infra.commodity.AttachmentBizType;
import com.ark.center.commodity.infra.commodity.convertor.SpuConverter;
import com.ark.component.common.util.assemble.FieldsAssembler;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommodityDetailsQryExe {

    private final SpuGateway spuGateway;
    private final SpuConverter spuConverter;
    private final SkuGateway skuGateway;
    private final CategoryGateway categoryGateway;
    private final AttachmentGateway attachmentGateway;

    public CommodityDTO execute(Long spuId) {

        Spu spu = spuGateway.selectById(spuId);
        if (spu == null) {
            return null;
        }

        CommodityDTO commodityDTO = spuConverter.toCommodityDTO(spu);

        assemblePics(commodityDTO);

        assembleSkus(commodityDTO);

        assembleAttrs(commodityDTO);

        assembleOthers(spu, commodityDTO);

        return commodityDTO;
    }

    private void assembleAttrs(CommodityDTO commodityDTO) {
        FieldsAssembler.execute(commodityDTO,
                CommodityDTO::getId,
                CommodityDTO::setParamList, id -> {
                    List<SpuAttr> attrs = spuGateway.selectAttrsBySpuId(id);
                    return spuConverter.toAttrDTO(attrs);
                });
    }

    private void assembleOthers(Spu spu, CommodityDTO commodityDTO) {
        FieldsAssembler.execute(commodityDTO,
                CommodityDTO::getId,
                CommodityDTO::setCategoryLevelPath, id -> categoryGateway.selectById(spu.getCategoryId()).getLevelPath());
    }

    private void assembleSkus(CommodityDTO commodityDTO) {
        FieldsAssembler.execute(commodityDTO,
                CommodityDTO::getId,
                CommodityDTO::setSkuList, spuId -> {
                    List<SkuDTO> skus = skuGateway.selectBySpuId(spuId);
                    for (SkuDTO skuDTO : skus) {
                        skuDTO.setSpecList();
                    }
                    return skuDTOS;
                });
    }

    private void assemblePics(CommodityDTO commodityDTO) {
        FieldsAssembler.execute(commodityDTO,
                CommodityDTO::getId,
                CommodityDTO::setPicList, id -> {
                    List<Attachment> attachments = attachmentGateway.selectByBizTypeAndBizId(AttachmentBizType.SPU_PIC, id);
                    if (CollectionUtils.isNotEmpty(attachments)) {
                        return attachments.stream().map(Attachment::getUrl).toList();
                    }
                    return Collections.emptyList();
                });
    }
}
