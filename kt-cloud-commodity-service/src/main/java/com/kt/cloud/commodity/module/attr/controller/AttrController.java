package com.kt.cloud.commodity.module.attr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kt.cloud.commodity.module.attr.dto.request.AttrCreateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.cloud.commodity.module.attr.service.AttrService;
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
 * 商品属性 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Api(tags = "商品属性")
@Validated
@RestController
@RequestMapping("/v1/attr")
public class AttrController extends BaseController {

    private final AttrService attrService;

    public AttrController(AttrService attrService) {
        this.attrService = attrService;
    }

    @ApiOperation(value = "创建商品属性")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrCreateReqDTO reqDTO) {
        return SingleResponse.ok(attrService.createAttr(reqDTO));
    }

    @ApiOperation(value = "修改商品属性")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated AttrUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attrService.updateAttr(reqDTO));
    }

    @ApiOperation(value = "查询商品属性分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrRespDTO>> pageList(@RequestBody @Validated AttrPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(attrService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询商品属性详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<AttrRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.getAttrInfo(id));
    }


}
