package com.ark.center.product.infra.product.es;

import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.infra.brand.gateway.BrandGateway;
import com.ark.center.product.infra.category.service.CategoryService;
import com.ark.center.product.infra.product.es.doc.SkuAttrDoc;
import com.ark.center.product.infra.product.es.doc.SkuDoc;
import com.ark.center.product.infra.spu.ShelfStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class GoodsEsSynchronizer {

    private final GoodsRepository goodsRepository;
    private final BrandGateway brandGateway;
    private final CategoryService categoryService;

    public void handleGoodsChangedEvent(GoodsDTO goodsDTO) {
        Integer shelfStatus = goodsDTO.getShelfStatus();

        List<SkuDTO> skus = goodsDTO.getSkus();

        if (shelfStatus.equals(ShelfStatus.DOWN.getValue())) {
            goodsRepository.deleteAllById(skus.stream().map(SkuDTO::getId).toList());
            return;
        }

        List<SkuDoc> skuDocs = skus.stream().map(sku -> {
            SkuDoc skuDoc = new SkuDoc();
            skuDoc.setSkuId(sku.getId());
            skuDoc.setSpuId(sku.getSpuId());
            skuDoc.setSkuName(sku.getName());
            skuDoc.setBrandName(brandGateway.selectById(goodsDTO.getBrandId()).getName());
            skuDoc.setCategoryName(categoryService.selectById(goodsDTO.getCategoryId()).getName());
            skuDoc.setBrandId(goodsDTO.getBrandId());
            skuDoc.setCategoryId(goodsDTO.getCategoryId());
            skuDoc.setSalesPrice(sku.getSalesPrice());
            skuDoc.setPictures(Collections.singletonList(goodsDTO.getMainPicture()));
            skuDoc.setCreateTime(ZonedDateTime.of(goodsDTO.getCreateTime(), ZoneId.systemDefault()));
            skuDoc.setUpdateTime(ZonedDateTime.of(goodsDTO.getUpdateTime(), ZoneId.systemDefault()));
            skuDoc.setAttrs(convertAttrs(sku));
            return skuDoc;
        }).toList();

        goodsRepository.saveAll(skuDocs);

    }

    public static void main(String[] args) {
        ZonedDateTime instant = LocalDateTime.now().atZone(ZoneId.of("+08:00"));
        System.out.println(instant);
    }

    private List<SkuAttrDoc> convertAttrs(SkuDTO sku) {
        return sku.getSpecs().stream().map(attr -> {
            SkuAttrDoc attrDoc = new SkuAttrDoc();
            attrDoc.setAttrId(attr.getAttrId());
            attrDoc.setAttrName(attr.getAttrName());
            attrDoc.setAttrValue(attr.getAttrValue());
            return attrDoc;
        }).toList();
    }
}
