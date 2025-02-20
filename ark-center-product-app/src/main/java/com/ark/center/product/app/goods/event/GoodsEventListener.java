package com.ark.center.product.app.goods.event;

import com.ark.center.product.app.goods.query.GoodsQryExe;
import com.ark.center.product.client.common.ProductConst;
import com.ark.center.product.client.goods.mq.GoodsChangedEventDTO;
import com.ark.center.product.infra.brand.gateway.BrandGateway;
import com.ark.center.product.infra.category.service.CategoryService;
import com.ark.center.product.infra.product.repository.es.GoodsRepository;
import com.ark.center.product.infra.product.service.SpuService;
import com.ark.center.product.infra.spu.ShelfStatus;
import com.ark.component.context.core.ServiceContext;
import com.ark.component.mq.MsgBody;
import com.ark.component.mq.SendConfirm;
import com.ark.component.mq.SendResult;
import com.ark.component.mq.integration.MessageTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


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
    private final SpuService spuService;
    private final CategoryService categoryService;
    private final BrandGateway brandGateway;
    private final GoodsQryExe goodsQryExe;
    private final MessageTemplate messageTemplate;

    public void onApplicationEvent(@NotNull GoodsShelfOnChangedEvent event) {
        log.info("Goods [{}] status has been changed ", event.getSpuId());
        Long spuId = event.getSpuId();
        ShelfStatus shelfStatus = event.getShelfStatus();
        GoodsChangedEventDTO goodsChangedEventDTO = new GoodsChangedEventDTO(spuId, shelfStatus.getValue());

        String traceId = ServiceContext.getTraceId();
        messageTemplate.asyncSend(ProductConst.TOPIC_PRODUCT, ProductConst.TAG_GOODS_PUBLISH_STATUS_CHANGED,
                MsgBody.of(traceId, goodsChangedEventDTO),

                new SendConfirm() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("Goods [{}] published event has been sent successfully  ", event.getSpuId());
                    }

                    @Override
                    public void onException(SendResult sendResult) {
                        log.info("Failed to send Goods [{}] published event", event.getSpuId());
                    }
                });
    }


}
