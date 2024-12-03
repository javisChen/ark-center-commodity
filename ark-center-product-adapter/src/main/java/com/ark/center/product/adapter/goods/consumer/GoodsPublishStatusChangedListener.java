package com.ark.center.product.adapter.goods.consumer;

import com.ark.center.product.app.goods.query.GoodsQryExe;
import com.ark.center.product.app.goods.service.GoodsCommandHandler;
import com.ark.center.product.client.common.ProductConst;
import com.ark.center.product.client.goods.mq.GoodsChangedEventDTO;
import com.ark.center.product.infra.product.es.GoodsEsSynchronizer;
import com.ark.center.product.infra.product.service.SpuService;
import com.ark.component.mq.MQType;
import com.ark.component.mq.core.annotations.MQMessageListener;
import com.ark.component.mq.core.processor.SimpleMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付单生成成功后通知
 */
@MQMessageListener(
        mq = MQType.ROCKET,
        consumerGroup = ProductConst.CG_TAG_GOODS_PUBLISH_STATUS_CHANGED,
        topic = ProductConst.TOPIC_PRODUCT,
        tags = ProductConst.TAG_GOODS_PUBLISH_STATUS_CHANGED
)
@Component
@Slf4j
@RequiredArgsConstructor
public class GoodsPublishStatusChangedListener extends SimpleMessageHandler<GoodsChangedEventDTO> {

    private final GoodsEsSynchronizer goodsEsSynchronizer;
    private final SpuService spuService;
    private final GoodsQryExe goodsQryExe;
    private final GoodsCommandHandler goodsCommandHandler;

    @Override
    protected void handleMessage(String msgId, String bizKey, GoodsChangedEventDTO eventDTO, Object o) {
        log.info("Received [GoodsPublishStatusChanged] message -> msgId = {}, bizKey = {}, eventDTO = {}", msgId, bizKey, eventDTO);
        Long spuId = eventDTO.getSpuId();
        try {

            goodsCommandHandler.handleGoodsChangedEvent(eventDTO);

            log.info("Goods {} has been synchronized successfully", spuId);
        } catch (Exception e) {
            log.error("Failed to sync goods {}", spuId, e);
            throw e;
        }
    }

}
