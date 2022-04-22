package com.kt.cloud.commodity.module.attr.controller;

import com.kt.cloud.commodity.module.attr.dto.request.*;
import com.kt.cloud.commodity.module.attr.dto.response.AttrGroupRespDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.cloud.commodity.module.attr.service.AttrService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.validator.ValidateGroup;
import com.kt.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    public SingleResponse<Long> create(@RequestBody @Validated AttrUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attrService.createAttr(reqDTO));
    }

    @ApiOperation(value = "修改商品属性")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) AttrUpdateReqDTO reqDTO) {
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
    public SingleResponse<AttrRespDTO> getInfo(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.getAttrInfo(id));
    }

    @ApiOperation(value = "创建商品属性组")
    @PostMapping("/group/create")
    public SingleResponse<Long> createGroup(@RequestBody @Validated AttrGroupCreateReqDTO reqDTO) {
        return SingleResponse.ok(attrService.createAttrGroup(reqDTO));
    }

    @ApiOperation(value = "修改商品属性组")
    @PostMapping("/group/update")
    public SingleResponse<Long> updateGroup(@RequestBody @Validated AttrGroupUpdateReqDTO reqDTO) {
        return SingleResponse.ok(attrService.updateAttrGroup(reqDTO));
    }

    @ApiOperation(value = "查询商品属性组分页列表")
    @PostMapping("/group/page")
    public SingleResponse<PageResponse<AttrGroupRespDTO>> getGroupPageList(@RequestBody @Validated AttrGroupPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(attrService.getAttrGroupPageList(queryDTO));
    }

    @ApiOperation(value = "查询商品属性组详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/group/info")
    public SingleResponse<AttrGroupRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.getAttrGroupInfo(id));
    }
}
