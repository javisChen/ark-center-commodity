package com.kt.cloud.commodity.module.commodity.controller;

import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.cloud.commodity.module.commodity.service.CommodityService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "商品")
@RestController
@RequestMapping("/v1/commodity")
public class CommodityController extends BaseController {

    private final CommodityService commodityService;

    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @ApiOperation(value = "创建")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated CommodityUpdateReqDTO reqDTO) {
        return SingleResponse.ok(commodityService.create(reqDTO));
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated CommodityUpdateReqDTO reqDTO) {
        return SingleResponse.ok(commodityService.updateCommodity(reqDTO));
    }

    @ApiOperation(value = "查询分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<CommodityPageRespDTO>> pageList(@RequestBody @Validated CommodityPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(commodityService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<CommodityPageRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(commodityService.getInfo(id));
    }


}
