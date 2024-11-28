package com.ark.center.product.adapter.goods.web;

import com.ark.center.product.app.goods.service.GoodsQueryService;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.client.goods.command.GoodsShelfCmd;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
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
@RequestMapping("/v1/admin/goods")
@RequiredArgsConstructor
public class GoodsAdminController extends BaseController {

    private final GoodsQueryService goodsQueryService;

    @Operation(summary = "保存商品")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated GoodsCmd cmd) {
        return SingleResponse.ok(goodsQueryService.save(cmd));
    }

    @Operation(summary = "保存商品")
    @PostMapping("/shelf")
    public ServerResponse updateShelf(@RequestBody @Validated GoodsShelfCmd cmd) {
        goodsQueryService.shelf(cmd);
        return SingleResponse.ok();
    }

    @Operation(summary = "查询分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<GoodsDTO>> queryPages(@RequestBody @Validated GoodsQry queryDTO) {
        return SingleResponse.ok(goodsQueryService.queryPages(queryDTO));
    }

    @Operation(summary = "查询详情")
    @GetMapping("/details")
    public SingleResponse<GoodsDTO> details(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(goodsQueryService.queryDetails(id));
    }

}
