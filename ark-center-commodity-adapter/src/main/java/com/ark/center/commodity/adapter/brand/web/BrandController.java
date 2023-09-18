package com.ark.center.commodity.adapter.brand.web;

import com.ark.center.commodity.app.brand.service.BrandApplicationService;
import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
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

    private final BrandApplicationService brandApplicationService;

    @Operation(summary = "新建品牌")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated BrandCreateCmd reqDTO) {
        return SingleResponse.ok(brandApplicationService.createBrand(reqDTO));
    }

    @Operation(summary = "修改品牌")
    @PostMapping("/update")
    public ServerResponse update(@RequestBody @Validated BrandUpdateCmd reqDTO) {
        brandApplicationService.updateBrand(reqDTO);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询品牌分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<BrandDTO>> queryPages(@RequestBody @Validated BrandPageQry queryDTO) {
        return SingleResponse.ok(brandApplicationService.queryPages(queryDTO));
    }

    @Operation(summary = "查询品牌详情")
    @GetMapping("/details")
    public SingleResponse<BrandDTO> details(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(brandApplicationService.getBrandInfo(id));
    }

}
