package com.ark.center.product.adapter.brand.web;

import com.ark.center.product.app.brand.BrandCommandHandler;
import com.ark.center.product.app.brand.BrandQueryService;
import com.ark.center.product.client.brand.command.BrandCmd;
import com.ark.center.product.client.brand.dto.BrandDTO;
import com.ark.center.product.client.brand.query.BrandPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "品牌")
@Validated
@RequestMapping("/v1/brand")
@RestController
@RequiredArgsConstructor
public class BrandController extends BaseController {

    private final BrandCommandHandler brandCommandHandler;
    private final BrandQueryService brandQueryService;

    @Operation(summary = "保存品牌")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated BrandCmd reqDTO) {
        return SingleResponse.ok(brandCommandHandler.handle(reqDTO));
    }

    @Operation(summary = "查询品牌分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<BrandDTO>> queryPages(@RequestBody @Validated BrandPageQry queryDTO) {
        return SingleResponse.ok(brandQueryService.queryPages(queryDTO));
    }

    @Operation(summary = "查询品牌详情")
    @GetMapping("/details")
    public SingleResponse<BrandDTO> details(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(brandQueryService.queryDetails(id));
    }
}
