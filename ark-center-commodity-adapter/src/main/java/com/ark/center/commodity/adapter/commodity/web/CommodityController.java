package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.app.commodity.service.CommodityAppService;
import com.ark.center.commodity.infra.commodity.gateway.es.CommodityDoc;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "商品接口（C端）")
@RestController
@RequestMapping("/v1/commodity")
@RequiredArgsConstructor
public class CommodityController extends BaseController {

    private final CommodityAppService commodityAppService;

    @Operation(summary = "查询分页列表")
    @PostMapping("/search")
    public MultiResponse<CommodityDoc> search() {
        List<CommodityDoc> search = commodityAppService.search();
        return MultiResponse.ok(search);
    }

    @Operation(summary = "初始化数据到ES")
    @PostMapping("/init")
    public ServerResponse init() {
        commodityAppService.initES();
        return ServerResponse.ok();
    }

}
