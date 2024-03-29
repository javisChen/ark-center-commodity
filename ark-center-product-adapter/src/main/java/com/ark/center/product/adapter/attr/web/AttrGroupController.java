package com.ark.center.product.adapter.attr.web;

import com.ark.center.product.app.attr.AttrApplicationService;
import com.ark.center.product.client.attr.command.AttrGroupCmd;
import com.ark.center.product.client.attr.dto.AttrGroupDTO;
import com.ark.center.product.client.attr.query.AttrGroupPageQry;
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
@Tag(name = "商品属性组")
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
