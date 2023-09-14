package com.ark.center.commodity.app.commodity.executor;

import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.attachment.Attachment;
import com.ark.center.commodity.domain.attachment.gateway.AttachmentGateway;
import com.ark.center.commodity.domain.category.Category;
import com.ark.center.commodity.domain.category.gateway.CategoryGateway;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.assembler.SpuAssembler;
import com.ark.center.commodity.domain.spu.gateway.SkuGateway;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.center.commodity.infra.commodity.AttachmentBizType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommodityDetailsQryExe {

    private final SpuGateway spuGateway;
    private final SkuGateway skuGateway;
    private final CategoryGateway categoryGateway;
    private final AttachmentGateway attachmentGateway;
    private final SpuAssembler spuAssembler;

    public CommodityDTO execute(Long spuId) {

        Spu spu = spuGateway.selectById(spuId);
        if (spu == null) {
            return null;
        }

        // base
        CommodityDTO commodityDTO = spuAssembler.toCommodityDTO(spu);

        // pic
        List<Attachment> attachments = attachmentGateway.selectByBizTypeAndBizId(AttachmentBizType.SPU_PIC, spuId);
        if (CollectionUtils.isNotEmpty(attachments)) {
            commodityDTO.setPicList(attachments.stream().map(Attachment::getUrl).toList());
        }

        // skus
        List<SkuDTO> skuList = skuGateway.selectBySpuId(spuId);
        commodityDTO.setSkuList(skuList);

        Category category = categoryGateway.selectById(spu.getCategoryId());
        commodityDTO.setCategoryLevelPath(category.getLevelPath());
        return commodityDTO;
    }
}
