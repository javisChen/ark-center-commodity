package com.kt.cloud.commodity.module.sku.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.sku.dto.request.SkuCreateReqDTO;
import com.kt.cloud.commodity.module.sku.dto.request.SkuUpdateReqDTO;
import com.kt.cloud.commodity.module.sku.dto.request.SkuPageQueryReqDTO;
import com.kt.cloud.commodity.module.sku.dto.response.SkuRespDTO;
import com.kt.cloud.commodity.module.sku.dto.response.SkuRespDTO;
import com.kt.cloud.commodity.module.sku.service.SkuService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.web.util.bean.BeanConvertor;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.kt.component.web.base.BaseController;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "sku")
@Validated
@RestController
@RequestMapping("/v1/sku")
public class SkuController extends BaseController {

    private final SkuService skuService;

    public SkuController(SkuService skuService) {
        this.skuService = skuService;
    }

    @ApiOperation(value = "创建")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated SkuCreateReqDTO reqDTO) {
        return SingleResponse.ok(skuService.createSku(reqDTO));
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated SkuUpdateReqDTO reqDTO) {
        return SingleResponse.ok(skuService.updateSku(reqDTO));
    }

    @ApiOperation(value = "查询分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<SkuRespDTO>> pageList(@RequestBody @Validated SkuPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(skuService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<SkuRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(skuService.getSkuInfo(id));
    }


}