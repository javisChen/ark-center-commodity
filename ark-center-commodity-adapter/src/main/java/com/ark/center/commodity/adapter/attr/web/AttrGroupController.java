package com.ark.center.commodity.adapter.attr.web;

import com.ark.center.commodity.app.attr.AttrApplicationService;
import com.ark.center.commodity.client.attr.command.AttrGroupCmd;
import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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
@Api(tags = "商品属性")
@Validated
@RestController
@RequestMapping("/v1/attr/group")
@RequiredArgsConstructor
public class AttrGroupController extends BaseController {

    private final AttrApplicationService attrService;

    @Operation(summary = "创建商品属性组")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated AttrGroupCmd reqDTO) {
        return SingleResponse.ok(attrService.saveAttrGroup(reqDTO));
    }

    @Operation(summary = "查询商品属性组分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<AttrGroupDTO>> queryPages(@RequestBody @Validated AttrGroupPageQry queryDTO) {
        return SingleResponse.ok(attrService.queryGroupPages(queryDTO));
    }

    @Operation(summary = "查询商品属性组详情")
    @GetMapping("/details")
    public SingleResponse<AttrGroupDTO> queryAttrGroupDetails(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.queryAttrGroupDetails(id));
    }

}
