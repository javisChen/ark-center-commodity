package com.ark.center.commodity.adapter.brand.web;

import com.ark.center.commodity.application.brand.service.BrandApplicationService;
import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@Api(tags = "商品品牌")
@Validated
@RequestMapping("/v1/brand")
@RestController
@RequiredArgsConstructor
public class BrandController extends BaseController {

    private final BrandApplicationService brandApplicationService;

    @ApiOperation(value = "新建商品")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated BrandCreateCmd reqDTO) {
        return SingleResponse.ok(brandApplicationService.createBrand(reqDTO));
    }

    @ApiOperation(value = "修改商品")
    @PostMapping("/update")
    public ServerResponse update(@RequestBody @Validated BrandUpdateCmd reqDTO) {
        brandApplicationService.updateBrand(reqDTO);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "查询商品分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<BrandDTO>> pageList(@RequestBody @Validated BrandPageQry queryDTO) {
        return SingleResponse.ok(brandApplicationService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询商品详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<BrandDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(brandApplicationService.getBrandInfo(id));
    }

}
