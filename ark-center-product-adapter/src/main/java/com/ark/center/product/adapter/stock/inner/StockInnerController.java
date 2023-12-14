package com.ark.center.product.adapter.stock.inner;

import com.ark.center.product.app.stock.StockAppService;
import com.ark.center.product.client.stock.StockApi;
import com.ark.center.product.client.stock.command.StockDecreaseCmd;
import com.ark.center.product.client.stock.dto.StockDecreaseDTO;
import com.ark.center.product.client.stock.dto.StockQuantityDTO;
import com.ark.center.product.client.stock.query.StockQuantityQry;
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
@RequestMapping("/v1/inner/stock")
@RequiredArgsConstructor
@Slf4j
public class StockInnerController implements StockApi {

    private final StockAppService stockAppService;

    /**
     * todo 暂不实现
     */
    @Override
    public MultiResponse<List<StockQuantityDTO>> queryStockQuantity(StockQuantityQry qry) {
        return null;
    }

    @Override
    public SingleResponse<StockDecreaseDTO> decreaseStock(StockDecreaseCmd cmd) {
        return stockAppService.decreaseStock(cmd);
    }
}
