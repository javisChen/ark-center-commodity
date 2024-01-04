package com.ark.center.product.app.goods.event;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.sku.gateway.SkuGateway;
import com.ark.center.product.domain.spu.ShelfStatus;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.center.product.infra.product.gateway.es.AttrDoc;
import com.ark.center.product.infra.product.gateway.es.GoodsRepository;
import com.ark.center.product.infra.product.gateway.es.SkuDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    private final SkuGateway skuGateway;
    private final SpuGateway spuGateway;

    public void onApplicationEvent(@NotNull GoodsShelfOnChangedEvent event) {
        log.info("Goods [{}] status has changed ", event.getSpuId());
        Long spuId = event.getSpuId();
        ShelfStatus shelfStatus = event.getShelfStatus();

        Spu spu = spuGateway.selectById(spuId);

        List<SkuDTO> skus = skuGateway.selectBySpuId(spuId);

        if (shelfStatus.equals(ShelfStatus.DOWN)) {
            goodsRepository.deleteAllById(skus.stream().map(SkuDTO::getId).toList());
        } else if (shelfStatus.equals(ShelfStatus.UP)) {
            List<SkuDoc> skuDocs = skus.stream().map(sku -> {
                SkuDoc skuDoc = new SkuDoc();
                skuDoc.setSkuId(sku.getId());
                skuDoc.setSpuId(sku.getSpuId());
                skuDoc.setSkuName(sku.getName());
                skuDoc.setBrandName("");
                skuDoc.setCategoryName("");
                skuDoc.setBrandId(spuId);
                skuDoc.setCategoryId(0L);
                skuDoc.setShowPrice(sku.getSalesPrice());
                skuDoc.setPictures(Collections.singletonList(spu.getMainPicture()));
                LocalDateTime updateTime = spu.getUpdateTime();
                skuDoc.setUpdateTime(updateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
                skuDoc.setAttrs(getSpecs(sku));
                return skuDoc;
            }).toList();
            goodsRepository.saveAll(skuDocs);
        } else {
            // todo
        }
    }

    public static void main(String[] args) {
        LocalDateTime now1 = LocalDateTime.now();
        LocalDateTime localDateTime = LocalDateTimeUtil.ofUTC(now1.toInstant(ZoneOffset.UTC));
        System.out.println(localDateTime);
        System.out.println(now1.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
    }

    private List<AttrDoc> getSpecs(SkuDTO sku) {
        return sku.getSpecs().stream().map(attr -> {
            AttrDoc attrDoc = new AttrDoc();
            attrDoc.setAttrId(attr.getAttrId());
            attrDoc.setAttrName(attr.getAttrName());
            attrDoc.setAttrValue(attr.getAttrValue());
            return attrDoc;
        }).toList();
    }

}
