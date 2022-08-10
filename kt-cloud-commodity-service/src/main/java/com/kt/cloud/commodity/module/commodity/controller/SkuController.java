package com.kt.cloud.commodity.module.commodity.controller;

import com.kt.cloud.commodity.api.sku.SkuApi;
import com.kt.cloud.commodity.api.sku.request.SkuInfoGetReqDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.cloud.commodity.module.commodity.service.SkuService;
import com.kt.component.dto.MultiResponse;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "商品")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/sku")
public class SkuController implements SkuApi {

    private final SkuService skuService;

    @Override
    public MultiResponse<SkuRespDTO> getSkuInfoList(SkuInfoGetReqDTO request) {
        return MultiResponse.ok(skuService.findByIds(request));
    }
}
