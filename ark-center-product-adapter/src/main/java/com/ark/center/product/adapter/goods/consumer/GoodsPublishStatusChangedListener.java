package com.ark.center.product.adapter.goods.consumer;

import com.ark.component.mq.MQType;
import com.ark.component.mq.core.annotations.MQMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单支付单生成成功后通知
 */
@MQMessageListener(
        mq = MQType.ROCKET,
        consumerGroup = PayConst.CG_TAG_PAY_ORDER_CREATED,
        topic = PayConst.TOPIC_PAY,
        tags = PayConst.TAG_PAY_ORDER_CREATED
)
@Component
@Slf4j
@RequiredArgsConstructor
public class GoodsPublishStatusChangedListener extends SimpleMessageHandler<PayOrderChangedEventDTO> {

    private final OrderAppService orderAppService;

    @Override
    protected void handleMessage(String msgId, String sendId, PayOrderChangedEventDTO eventDTO, Object o) {
        log.info("Received [PayOrderCreated] message -> msgId = {}, sendId = {}, eventDTO = {}", msgId, sendId, eventDTO);
        String bizTradeNo = eventDTO.getBizTradeNo();
        try {
            orderAppService.onPayOrderCreated(eventDTO);
            log.info("Order {} has been updated successfully", bizTradeNo);
        } catch (Exception e) {
            log.error("Failed to update order {}", bizTradeNo, e);
            throw e;
        }
    }
}
