package com.ark.center.commodity.app.stock;

import com.ark.center.commodity.app.stock.executor.StockDecreaseCmdExe;
import com.ark.center.commodity.client.stock.command.StockDecreaseCmd;
import com.ark.center.commodity.client.stock.dto.StockDecreaseDTO;
import com.ark.component.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockAppService {

    private final StockDecreaseCmdExe stockDecreaseCmdExe;

    @Transactional(rollbackFor = Throwable.class)
    public SingleResponse<StockDecreaseDTO> decreaseStock(StockDecreaseCmd cmd) {
        return SingleResponse.ok(stockDecreaseCmdExe.execute(cmd));
    }

}
