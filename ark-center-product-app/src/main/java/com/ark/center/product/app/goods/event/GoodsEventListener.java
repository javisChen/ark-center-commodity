package com.ark.center.product.app.goods.event;

import com.ark.center.product.app.goods.query.GoodsQryExe;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.infra.brand.gateway.BrandGateway;
import com.ark.center.product.infra.category.service.CategoryService;
import com.ark.center.product.infra.product.es.doc.AttrDoc;
import com.ark.center.product.infra.product.es.doc.AttrOptionDoc;
import com.ark.center.product.infra.product.es.GoodsRepository;
import com.ark.center.product.infra.product.es.doc.SkuDoc;
import com.ark.center.product.infra.sku.SkuService;
import com.ark.center.product.infra.spu.ShelfStatus;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.product.service.SpuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;


/**
 * 商品事件监听
 *
 * @author JC
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GoodsEventListener implements ApplicationListener<GoodsShelfOnChangedEvent> {

    private final GoodsRepository goodsRepository;
    private final SkuService skuService;
    private final SpuService spuService;
    private final CategoryService categoryService;
    private final BrandGateway brandGateway;
    private final GoodsQryExe goodsQryExe;

    public void onApplicationEvent(@NotNull GoodsShelfOnChangedEvent event) {
        log.info("Goods [{}] status has been changed ", event.getSpuId());
        Long spuId = event.getSpuId();
        ShelfStatus shelfStatus = event.getShelfStatus();

        Spu spu = spuService.selectById(spuId);

        GoodsDTO goodsDTO = goodsQryExe.queryDetails(spuId);

        List<SkuDTO> skus = goodsDTO.getSkus();

        if (shelfStatus.equals(ShelfStatus.DOWN)) {
            goodsRepository.deleteAllById(skus.stream().map(SkuDTO::getId).toList());
        } else if (shelfStatus.equals(ShelfStatus.UP)) {
            List<SkuDoc> skuDocs = skus.stream().map(sku -> {
                SkuDoc skuDoc = new SkuDoc();
                skuDoc.setSkuId(sku.getId());
                skuDoc.setSpuId(sku.getSpuId());
                skuDoc.setSkuName(sku.getName());
                skuDoc.setBrandName(brandGateway.selectById(spu.getBrandId()).getName());
                skuDoc.setCategoryName(categoryService.selectById(spu.getCategoryId()).getName());
                skuDoc.setBrandId(spu.getBrandId());
                skuDoc.setCategoryId(spu.getCategoryId());
                skuDoc.setSalesPrice(sku.getSalesPrice());
                skuDoc.setPictures(Collections.singletonList(spu.getMainPicture()));
                skuDoc.setCreateTime(ZonedDateTime.of(spu.getCreateTime(), ZoneId.systemDefault()));
                skuDoc.setUpdateTime(ZonedDateTime.of(spu.getUpdateTime(), ZoneId.systemDefault()));
                skuDoc.setAttrs(getSpecs(sku));
                return skuDoc;
            }).toList();
            goodsRepository.saveAll(skuDocs);
        } else {
            // todo
        }
    }

    public static void main(String[] args) {
        ZonedDateTime instant = LocalDateTime.now().atZone(ZoneId.of("+08:00"));
        System.out.println(instant);
    }

    private LocalDateTime toUTC(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    private List<AttrDoc> getSpecs(SkuDTO sku) {
        return sku.getSpecs().stream().map(attr -> {
            AttrDoc attrDoc = new AttrDoc();
            attrDoc.setAttrId(attr.getAttrId());
            attrDoc.setAttrName(attr.getAttrName());
            attrDoc.setAttrValue(attr.getAttrValue());
            List<AttrOptionDoc> optionDocs = attr.getOptionList().stream().map(option -> {
                AttrOptionDoc attrOptionDoc = new AttrOptionDoc();
                attrOptionDoc.setValue(option.getValue());
                return attrOptionDoc;
            }).toList();
            attrDoc.setOptionList(optionDocs);
            return attrDoc;
        }).toList();
    }

}
