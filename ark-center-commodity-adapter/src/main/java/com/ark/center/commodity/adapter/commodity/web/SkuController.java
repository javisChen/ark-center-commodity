package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.app.commodity.service.SkuAppService;
import com.ark.center.commodity.client.commodity.command.SkuStockReduceCmd;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.center.commodity.client.commodity.rpc.SkuApi;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
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
@Tag(name = "Sku", description = "Sku管理接口")
@Validated
@RestController
@RequestMapping("/v1/sku")
@RequiredArgsConstructor
@Slf4j
public class SkuController implements SkuApi {

    private final SkuAppService skuAppService;

    @Override
    public MultiResponse<SkuDTO> querySkus(SkuQry qry) {
        return MultiResponse.ok(skuAppService.querySkuList(qry));
    }

    @Override
    public ServerResponse decreaseStock(List<SkuStockReduceCmd> cmd) {
        return skuAppService.decreaseStock(cmd);
    }
}
