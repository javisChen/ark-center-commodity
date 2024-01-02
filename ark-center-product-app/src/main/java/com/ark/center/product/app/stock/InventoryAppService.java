package com.ark.center.product.app.stock;

import com.ark.center.product.app.stock.executor.InventoryLockCmdExe;
import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.component.dto.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InventoryAppService {

    private final InventoryLockCmdExe inventoryLockCmdExe;

    @Transactional(rollbackFor = Throwable.class)
    public ServerResponse lock(StockLockCmd cmd) {
        inventoryLockCmdExe.execute(cmd);
        return ServerResponse.ok();
    }

}
