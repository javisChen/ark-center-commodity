package com.kt.cloud.commodity.module.attr.controller;

import com.kt.cloud.commodity.module.attr.dto.request.AttrGroupCreateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrGroupPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrGroupUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrGroupRespDTO;
import com.kt.cloud.commodity.module.attr.service.AttrGroupService;
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
 * 商品属性组 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "商品属性组")
@Validated
@RestController
@RequestMapping("/v1/attr-group")
public class AttrGroupController extends BaseController {

    private final AttrGroupService attrGroupService;

    public AttrGroupController(AttrGroupService attrGroupService) {
        this.attrGroupService = attrGroupService;
    }

    @ApiOperation(value = "创建商品属性组")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrGroupCreateReqDTO reqDTO) {
        return SingleResponse.ok(attrGroupService.createAttrGroup(reqDTO));
    }

    @ApiOperation(value = "修改商品属性组")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated AttrGroupUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attrGroupService.updateAttrGroup(reqDTO));
    }

    @ApiOperation(value = "查询商品属性组分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrGroupRespDTO>> pageList(@RequestBody @Validated AttrGroupPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(attrGroupService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询商品属性组详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<AttrGroupRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrGroupService.getAttrGroupInfo(id));
    }


}
