package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.app.commodity.service.CommodityAppService;
import com.ark.center.commodity.client.commodity.command.CommodityCreateCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "商品接口")
@RestController
@RequestMapping("/v1/admin/commodity")
@RequiredArgsConstructor
public class CommodityAdminController extends BaseController {

    private final CommodityAppService commodityAppService;

    @Operation(summary = "保存商品")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated CommodityCreateCmd reqDTO) {
        return SingleResponse.ok(commodityAppService.save(reqDTO));
    }

    @Operation(summary = "查询分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<CommodityPageDTO>> queryPages(@RequestBody @Validated CommodityPageQry queryDTO) {
        return SingleResponse.ok(commodityAppService.queryPages(queryDTO));
    }

    @Operation(summary = "查询详情")
    @GetMapping("/details")
    public SingleResponse<CommodityDTO> details(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(commodityAppService.queryDetails(id));
    }

}
