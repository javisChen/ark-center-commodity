package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.app.commodity.service.SkuAppService;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.client.commodity.query.SkuQry;
import com.ark.center.commodity.client.commodity.rpc.SkuApi;
import com.ark.component.dto.MultiResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * SKU
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Api(tags = "SKU接口")
@Validated
@RestController
@RequestMapping("/v1/sku")
@RequiredArgsConstructor
@Slf4j
public class SkuController implements SkuApi {

    private final SkuAppService skuAppService;

    @Override
    public MultiResponse<SkuDTO> getSkuList(SkuQry qry) {
        return MultiResponse.ok(skuAppService.querySkuList(qry));
    }
}
