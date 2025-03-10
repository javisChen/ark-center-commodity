package com.ark.center.product.infra.product.builder;

import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.infra.attachment.Attachment;
import com.ark.center.product.infra.attachment.gateway.AttachmentGateway;
import com.ark.center.product.infra.attr.AttrOption;
import com.ark.center.product.infra.brand.gateway.BrandGateway;
import com.ark.center.product.infra.category.service.CategoryService;
import com.ark.center.product.infra.product.AttachmentBizType;
import com.ark.center.product.infra.product.service.SpuService;
import com.ark.center.product.infra.sku.SkuService;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.spu.SpuSales;
import com.ark.center.product.infra.spu.assembler.SpuAssembler;
import com.ark.component.common.util.assemble.DataProcessor;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoodsBuilder {

    private final SpuService spuService;

    private final SpuAssembler spuAssembler;

    private final SkuService skuService;

    private final CategoryService categoryService;

    private final AttachmentGateway attachmentGateway;

    private final BrandGateway brandGateway;

    public List<GoodsDTO> build(List<Spu> records, GoodsBuildProfiles profiles) {

        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        List<GoodsDTO> goodsDTOS = spuAssembler.toGoodsDTO(records);

        DataProcessor<GoodsDTO> dataProcessor = new DataProcessor<>(goodsDTOS);

        assembleNames(profiles, dataProcessor);

        assemblePics(profiles, dataProcessor, goodsDTOS);

        assembleSkus(profiles, dataProcessor);

        assembleSales(profiles, dataProcessor);

        assembleSpecs(profiles, dataProcessor);

        assembleCategoryInfo(profiles, dataProcessor, goodsDTOS);

        return goodsDTOS;
    }

    private void assembleNames(GoodsBuildProfiles profiles, DataProcessor<GoodsDTO> dataProcessor) {

        dataProcessor.keySelect(GoodsDTO::getBrandId)
                .query(brandGateway::selectByIds)
                .keyBy(BaseEntity::getId)
                .object()
                .process((goods, brand) -> goods.setBrandName(brand.getName()));

    }

    private void assembleSpecs(GoodsBuildProfiles profiles, DataProcessor<GoodsDTO> dataProcessor) {
        dataProcessor.keySelect(GoodsDTO::getId)
                .query(spuIds -> spuService.querySpecWithOptions(spuIds, AttrOption.Type.EXCLUSIVE))
                .keyBy(GoodsAttrDTO::getSpuId)
                .collection()
                .process(GoodsDTO::setSpecs);

    }

    private void assembleSales(GoodsBuildProfiles profiles, DataProcessor<GoodsDTO> dataProcessor) {
        if (profiles.getWithSaleInfo()) {
            dataProcessor.keySelect(GoodsDTO::getId)
                    .query(spuService::selectSalesBySpuIds)
                    .keyBy(SpuSales::getSpuId)
                    .object()
                    .process((goodsDTO, spuSales) -> goodsDTO.setParams(spuAssembler.toGoodsAttr(spuSales.getParamData())));
        }
    }

    private void assembleCategoryInfo(GoodsBuildProfiles profiles, DataProcessor<GoodsDTO> dataProcessor, List<GoodsDTO> goodsDTOS) {
        dataProcessor
                .keySelect(GoodsDTO::getCategoryId)
                .query(categoryService::selectByIds)
                .keyBy(BaseEntity::getId)
                .object()
                .process((goods, category) -> {
                    goods.setCategoryLevelPath(category.getLevelPath());
                    goods.setCategoryName(category.getName());
                });
    }

    private void assembleSkus(GoodsBuildProfiles profiles, DataProcessor<GoodsDTO> dataProcessor) {

        dataProcessor
                .keySelect(GoodsDTO::getId)
                .query(skuService::selectBySpuIds)
                .keyBy(SkuDTO::getSpuId)
                .collection()
                .process(GoodsDTO::setSkus);

    }

    private void assemblePics(GoodsBuildProfiles profiles, DataProcessor<GoodsDTO> dataProcessor, List<GoodsDTO> goodsDTO) {

        dataProcessor
                .keySelect(GoodsDTO::getId)
                .query(goodsIds -> attachmentGateway.selectByBizTypeAndBizIds(AttachmentBizType.SPU_PIC, goodsIds))
                .keyBy(Attachment::getBizId)
                .collection()
                .process((goodsDTO1, attachments) -> goodsDTO1.setPictures(attachments.stream().map(Attachment::getUrl).toList()));
    }

    public GoodsDTO build(Spu spu, GoodsBuildProfiles profiles) {
        return build(Lists.newArrayList(spu), profiles).getFirst();
    }
}
