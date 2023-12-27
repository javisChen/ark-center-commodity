package com.ark.center.product.app.stock;

import com.ark.center.product.app.stock.executor.InventoryLockCmdExe;
import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.product.client.inventory.dto.StockLockDTO;
import com.ark.center.product.domain.inventory.service.InventoryService;
import com.ark.component.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InventoryAppService {

    private final InventoryLockCmdExe inventoryLockCmdExe;
    private final InventoryService inventoryService;

    @Transactional(rollbackFor = Throwable.class)
    public SingleResponse<StockLockDTO> lock(StockLockCmd cmd) {
        return SingleResponse.ok(inventoryService.lockStock(cmd));
    }

}
