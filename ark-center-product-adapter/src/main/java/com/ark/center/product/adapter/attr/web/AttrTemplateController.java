package com.ark.center.product.adapter.attr.web;

import com.ark.center.product.app.attr.AttrApplicationService;
import com.ark.center.product.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.product.client.attr.dto.AttrTemplateDTO;
import com.ark.center.product.client.attr.query.AttrTemplatePageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品属性模板 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Tag(name = "商品属模板性")
@Validated
@RestController
@RequestMapping("/v1/attr/template")
@RequiredArgsConstructor
public class AttrTemplateController extends BaseController {

    private final AttrApplicationService attrApplicationService;

    @Operation(summary = "创建属性模板")
    @PostMapping("/save")
    public SingleResponse<Long> create(@RequestBody @Validated AttrTemplateCreateCmd reqDTO) {
        return SingleResponse.ok(attrApplicationService.saveAttrTemplate(reqDTO));
    }

    @Operation(summary = "查询属性模板分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<AttrTemplateDTO>> queryPages(@RequestBody @Validated AttrTemplatePageQry queryDTO) {
        return SingleResponse.ok(attrApplicationService.queryAttrTemplatePages(queryDTO));
    }

    @Operation(summary = "查询属性模板详情")
    @GetMapping("/details")
    public SingleResponse<AttrTemplateDTO> queryDetails(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrApplicationService.queryAttrTemplateDetails(id));
    }


}
