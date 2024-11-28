package com.ark.center.product.adapter.goods.consumer;

import com.ark.center.product.app.goods.query.GoodsQryExe;
import com.ark.center.product.client.common.ProductConst;
import com.ark.center.product.client.goods.dto.GoodsDTO;
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

    @Override
    protected void handleMessage(String msgId, String sendId, GoodsChangedEventDTO eventDTO, Object o) {
        log.info("Received [PayOrderCreated] message -> msgId = {}, sendId = {}, eventDTO = {}", msgId, sendId, eventDTO);
        Long spuId = eventDTO.getSpuId();
        try {

            GoodsDTO goodsDTO = goodsQryExe.queryDetails(spuId);

            goodsEsSynchronizer.handleGoodsChangedEvent(goodsDTO);

            log.info("Goods {} has been synchronized successfully", spuId);
        } catch (Exception e) {
            log.error("Failed to sync goods {}", spuId, e);
            throw e;
        }
    }
}
