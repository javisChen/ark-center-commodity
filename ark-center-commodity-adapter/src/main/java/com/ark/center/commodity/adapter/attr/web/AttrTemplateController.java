package com.ark.center.commodity.adapter.attr.web;

import com.ark.center.commodity.application.attr.service.AttrApplicationService;
import com.ark.center.commodity.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrTemplateUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
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

/**
 * <p>
 * 商品属性模板 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Api(tags = "商品属性")
@Validated
@RestController
@RequestMapping("/v1/attr/template")
@RequiredArgsConstructor
public class AttrTemplateController extends BaseController {
    private final AttrApplicationService attrApplicationService;

    @ApiOperation(value = "创建属性模板")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrTemplateCreateCmd reqDTO) {
        return SingleResponse.ok(attrApplicationService.createAttrTemplate(reqDTO));
    }

    @ApiOperation(value = "编辑属性模板")
    @PostMapping("/update")
    public ServerResponse update(@RequestBody @Validated AttrTemplateUpdateCmd reqDTO) {
        attrApplicationService.updateAttrTemplate(reqDTO);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "查询属性模板分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrTemplateDTO>> pageList(@RequestBody @Validated AttrTemplatePageQry queryDTO) {
        return SingleResponse.ok(attrApplicationService.getAttrTemplatePageList(queryDTO));
    }

    @ApiOperation(value = "查询属性模板详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<AttrTemplateDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrApplicationService.getAttrTemplateInfo(id));
    }


}