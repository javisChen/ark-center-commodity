package com.ark.center.product.app.goods.executor;

import com.ark.center.product.app.goods.event.GoodsShelfOnChangedEvent;
import com.ark.center.product.client.goods.command.GoodsShelfCmd;
import com.ark.center.product.domain.spu.ShelfStatus;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 商品上下架命令执行器
 */
@Component
@RequiredArgsConstructor
public class GoodsShelfCmdExe {

    private final SpuGateway spuGateway;
    private final ApplicationEventPublisher eventPublisher;

    public void execute(GoodsShelfCmd cmd) {

        Integer shelfStatus = cmd.getShelfStatus();
        Long spuId = cmd.getId();

        Spu spu = new Spu();
        spu.setShelfStatus(shelfStatus);
        spu.setId(spuId);
        spuGateway.updateSpu(spu);

        eventPublisher.publishEvent(new GoodsShelfOnChangedEvent(spuId, ShelfStatus.getByValue(shelfStatus)));
    }


}
