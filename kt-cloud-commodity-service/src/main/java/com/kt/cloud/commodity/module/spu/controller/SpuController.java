package com.kt.cloud.commodity.module.spu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.spu.dto.request.SpuCreateReqDTO;
import com.kt.cloud.commodity.module.spu.dto.request.SpuUpdateReqDTO;
import com.kt.cloud.commodity.module.spu.dto.request.SpuPageQueryReqDTO;
import com.kt.cloud.commodity.module.spu.dto.response.SpuRespDTO;
import com.kt.cloud.commodity.module.spu.dto.response.SpuRespDTO;
import com.kt.cloud.commodity.module.spu.service.SpuService;
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
 * spu主表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "spu主表")
@Validated
@RestController
@RequestMapping("/v1/spu")
public class SpuController extends BaseController {

    private final SpuService spuService;

    public SpuController(SpuService spuService) {
        this.spuService = spuService;
    }

    @ApiOperation(value = "创建")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated SpuCreateReqDTO reqDTO) {
        return SingleResponse.ok(spuService.createSpu(reqDTO));
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated SpuUpdateReqDTO reqDTO) {
        return SingleResponse.ok(spuService.updateSpu(reqDTO));
    }

    @ApiOperation(value = "查询分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<SpuRespDTO>> pageList(@RequestBody @Validated SpuPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(spuService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<SpuRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(spuService.getSpuInfo(id));
    }


}
