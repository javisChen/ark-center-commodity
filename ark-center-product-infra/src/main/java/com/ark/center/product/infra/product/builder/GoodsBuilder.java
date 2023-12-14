package com.ark.center.product.infra.product.builder;

import com.alibaba.fastjson2.JSON;
import com.ark.center.product.client.goods.dto.AttrDTO;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.attachment.Attachment;
import com.ark.center.product.domain.attachment.gateway.AttachmentGateway;
import com.ark.center.product.domain.brand.gateway.BrandGateway;
import com.ark.center.product.domain.category.gateway.CategoryGateway;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.SpuSales;
import com.ark.center.product.domain.spu.gateway.SkuGateway;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.center.product.domain.spu.service.SpuService;
import com.ark.center.product.infra.product.AttachmentBizType;
import com.ark.center.product.infra.product.convertor.SpuConverter;
import com.ark.component.common.util.assemble.FieldsAssembler;
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

    private final SpuGateway spuGateway;

    private final SpuService spuService;

    private final SpuConverter spuConverter;

    private final SkuGateway skuGateway;

    private final CategoryGateway categoryGateway;

    private final AttachmentGateway attachmentGateway;

    private final BrandGateway brandGateway;

    public List<GoodsDTO> build(List<Spu> records, GoodsBuildProfiles profiles) {

        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        List<GoodsDTO> goodsDTO = spuConverter.toGoodsDTO(records);

        assembleNames(profiles, goodsDTO);

        assemblePics(profiles, goodsDTO);

        assembleSkus(profiles, goodsDTO);

        assembleSales(profiles, goodsDTO);

        assembleSpecs(profiles, goodsDTO);

        assembleOthers(profiles, goodsDTO);

        return goodsDTO;
    }

    private void assembleNames(GoodsBuildProfiles profiles, List<GoodsDTO> goodsDTOS) {
        FieldsAssembler.execute(
                goodsDTOS,
                GoodsDTO::getBrandId,
                brandGateway::selectByIds,
                (goodsDTO, brands) -> goodsDTO.setBrandName(brands.get(0).getName()),
                BaseEntity::getId
        );
    }

    private void assembleSpecs(GoodsBuildProfiles profiles, List<GoodsDTO> goodsDTOS) {
        FieldsAssembler.execute(
                goodsDTOS,
                GoodsDTO::getId,
                spuService::querySpecs,
                GoodsDTO::setSpecs,
                AttrDTO::getSpuId
        );
    }

    private void assembleSales(GoodsBuildProfiles profiles, List<GoodsDTO> goodsDTOS) {
        if (profiles.getWithSaleInfo()) {
            GoodsDTO goodsDTO = goodsDTOS.get(0);
            SpuSales sales = spuGateway.selectSalesBySpuId(goodsDTO.getId());
            FieldsAssembler.execute(goodsDTO,
                    GoodsDTO::getId,
                    GoodsDTO::setParams, id -> JSON.parseArray(sales.getParamData(), AttrDTO.class));
        }
    }

    private void assembleOthers(GoodsBuildProfiles profiles, List<GoodsDTO> goodsDTOS) {
        FieldsAssembler.execute(
                goodsDTOS,
                GoodsDTO::getCategoryId,
                categoryGateway::selectByIds,
                (goodsDTO, categories) -> {
                    if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(categories)) {
                        goodsDTO.setCategoryLevelPath(categories.get(0).getLevelPath());
                        goodsDTO.setCategoryName(categories.get(0).getName());
                    }
                },
                BaseEntity::getId
        );
    }

    private void assembleSkus(GoodsBuildProfiles profiles, List<GoodsDTO> goodsDTO) {
        FieldsAssembler.execute(goodsDTO,
                GoodsDTO::getId,
                skuGateway::selectBySpuIds,
                GoodsDTO::setSkus,
                SkuDTO::getSpuId);
    }

    private void assemblePics(GoodsBuildProfiles profiles, List<GoodsDTO> goodsDTO) {
        FieldsAssembler.execute(
                profiles.getWithPictures(),
                goodsDTO,
                GoodsDTO::getId,
                spuIds -> attachmentGateway.selectByBizTypeAndBizIds(AttachmentBizType.SPU_PIC, spuIds),
                (goodsDTO1, attachments) -> goodsDTO1.setPictures(attachments.stream().map(Attachment::getUrl).toList()),
                Attachment::getBizId
        );
    }

    public GoodsDTO build(Spu spu, GoodsBuildProfiles profiles) {
        return build(Lists.newArrayList(spu), profiles).get(0);
    }
}
