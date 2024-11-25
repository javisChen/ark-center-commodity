package com.ark.center.product.app.goods.event;

import com.ark.center.product.infra.spu.ShelfStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 角色API权限变更事件
 * @author JC
 */
@Getter
@Setter
public class GoodsShelfOnChangedEvent extends ApplicationEvent {

    private final Long spuId;
    private final ShelfStatus shelfStatus;

    public GoodsShelfOnChangedEvent(Long spuId, ShelfStatus shelfStatus) {
        super(Clock.systemUTC());
        this.spuId = spuId;
        this.shelfStatus = shelfStatus;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

}
