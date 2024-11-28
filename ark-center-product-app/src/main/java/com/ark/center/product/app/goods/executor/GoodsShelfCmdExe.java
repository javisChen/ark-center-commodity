package com.ark.center.product.app.goods.executor;

import com.ark.center.product.app.goods.event.GoodsShelfOnChangedEvent;
import com.ark.center.product.client.goods.command.GoodsShelfCmd;
import com.ark.center.product.infra.spu.ShelfStatus;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.product.service.SpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 商品上下架命令执行器
 */
@Component
@RequiredArgsConstructor
public class GoodsShelfCmdExe {

    private final SpuService spuService;
    private final ApplicationEventPublisher eventPublisher;

    public void execute(GoodsShelfCmd cmd) {

        Integer shelfStatus = cmd.getShelfStatus();
        Long spuId = cmd.getId();

        Spu spu = new Spu();
        spu.setShelfStatus(shelfStatus);
        spu.setId(spuId);
        spuService.updateSpu(spu);

        eventPublisher.publishEvent(new GoodsShelfOnChangedEvent(spuId, ShelfStatus.getByValue(shelfStatus)));
    }


}
