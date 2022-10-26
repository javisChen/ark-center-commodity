package com.ark.center.commodity.adapter.commodity.web;

import com.ark.center.commodity.application.commodity.service.CommodityAdminApplicationService;
import com.ark.center.commodity.client.commodity.command.CommoditySaveCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.dto.SearchDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "管理端-商品")
@RestController
@RequestMapping("/v1/admin/commodity")
@RequiredArgsConstructor
public class CommodityAdminController extends BaseController {

    private final CommodityAdminApplicationService commodityAdminApplicationService;

    @ApiOperation(value = "创建商品")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated CommoditySaveCmd reqDTO) {
        return SingleResponse.ok(commodityAdminApplicationService.save(reqDTO));
    }

    @ApiOperation(value = "修改商品")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated CommoditySaveCmd reqDTO) {
        return SingleResponse.ok(commodityAdminApplicationService.save(reqDTO));
    }

    @ApiOperation(value = "查询分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<CommodityPageDTO>> pageList(@RequestBody @Validated CommodityPageQry queryDTO) {
        return SingleResponse.ok(commodityAdminApplicationService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<CommodityDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(commodityAdminApplicationService.getInfo(id));
    }

    @ApiOperation(value = "查询分页列表")
    @PostMapping("/search")
    public SingleResponse<SearchDTO> pageList() {
        return SingleResponse.ok(commodityAdminApplicationService.search());
    }

}
