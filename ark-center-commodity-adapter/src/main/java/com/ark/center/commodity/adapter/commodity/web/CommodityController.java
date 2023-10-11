package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.app.commodity.service.CommodityAppService;
import com.ark.center.commodity.client.category.dto.HomeCategoryDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.infra.commodity.gateway.es.CommodityDoc;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Tag(name = "商品接口（C端）")
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

    @Operation(summary = "查询首页分类")
    @GetMapping("/categories")
    public MultiResponse<HomeCategoryDTO> homeCategories() {
        List<HomeCategoryDTO> search = commodityAppService.queryHomeCategories();
        return MultiResponse.ok(search);
    }

    @Operation(summary = "查询详情")
    @GetMapping("/details")
    public SingleResponse<CommodityDTO> details(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(commodityAppService.queryDetails(id));
    }

    @Operation(summary = "初始化数据到ES")
    @PostMapping("/init")
    public ServerResponse init() {
        commodityAppService.initES();
        return ServerResponse.ok();
    }

}
