package com.ark.center.product.adapter.inventory.inner;

import com.ark.center.product.app.stock.InventoryAppService;
import com.ark.center.product.client.inventory.InventoryApi;
import com.ark.center.product.client.inventory.command.StockLockCmd;
import com.ark.center.product.client.inventory.dto.StockLockDTO;
import com.ark.center.product.client.inventory.dto.StockQuantityDTO;
import com.ark.center.product.client.inventory.query.StockQuantityQry;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * SKU
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Tag(name = "库存接口（内部）", description = "库存接口（内部）")
@Validated
@RestController
@RequestMapping("/v1/inner/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryInnerController implements InventoryApi {

    private final InventoryAppService inventoryAppService;

    /**
     * todo 暂不实现
     */
    @Override
    public MultiResponse<List<StockQuantityDTO>> queryStockQuantity(StockQuantityQry qry) {
        return null;
    }

    @Override
    public SingleResponse<StockLockDTO> lock(StockLockCmd cmd) {
        return inventoryAppService.lock(cmd);
    }
}
