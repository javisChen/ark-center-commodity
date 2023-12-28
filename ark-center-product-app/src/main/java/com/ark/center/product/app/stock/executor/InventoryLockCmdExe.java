package com.ark.center.product.app.stock.executor;

import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.product.client.inventory.dto.StockLockDTO;
import com.ark.center.product.domain.inventory.service.InventoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class InventoryLockCmdExe {

    private final InventoryService inventoryService;

    /**
     * todo 需要优化，目前是比较简单的实现
     */
    public StockLockDTO execute(StockLockCmd cmd) {
        return inventoryService.lockStock(cmd);
    }

}
