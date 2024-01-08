package com.ark.center.product.adapter.goods.web;

import com.ark.center.product.app.goods.service.SkuAppService;
import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.client.goods.query.SkuQry;
import com.ark.center.product.client.goods.rpc.SkuApi;
import com.ark.component.dto.MultiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

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
@RequiredArgsConstructor
@Slf4j
public class SkuController implements SkuApi {

    private final SkuAppService skuAppService;

    @Override
    public MultiResponse<SkuDTO> querySkus(SkuQry qry) {
        return MultiResponse.ok(skuAppService.querySkuList(qry));
    }

}
