package com.kt.cloud.commodity.module.attrvalue.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.attrvalue.dto.request.AttrValueCreateReqDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.request.AttrValueUpdateReqDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.request.AttrValuePageQueryReqDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.response.AttrValueRespDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.response.AttrValueRespDTO;
import com.kt.cloud.commodity.module.attrvalue.service.AttrValueService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
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
 * 商品属性值 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Api(tags = "商品属性值")
@Validated
@RestController
@RequestMapping("/v1/attr-value")
public class AttrValueController extends BaseController {

    private final AttrValueService attrValueService;

    public AttrValueController(AttrValueService attrValueService) {
        this.attrValueService = attrValueService;
    }

    @ApiOperation(value = "创建商品属性值")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrValueCreateReqDTO reqDTO) {
        return SingleResponse.ok(attrValueService.createAttrValue(reqDTO));
    }

    @ApiOperation(value = "修改商品属性值")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated AttrValueUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attrValueService.updateAttrValue(reqDTO));
    }

    @ApiOperation(value = "查询商品属性值分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrValueRespDTO>> pageList(@RequestBody @Validated AttrValuePageQueryReqDTO queryDTO) {
        return SingleResponse.ok(attrValueService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询商品属性值详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<AttrValueRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrValueService.getAttrValueInfo(id));
    }


}
