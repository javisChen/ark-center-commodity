package com.ark.center.product.adapter.attr.web;

import com.ark.center.product.app.attr.AttrApplicationService;
import com.ark.center.product.app.attr.AttrCommandHandler;
import com.ark.center.product.app.attr.AttrQueryService;
import com.ark.center.product.client.attr.command.AttrCreateCmd;
import com.ark.center.product.client.attr.dto.AttrDTO;
import com.ark.center.product.client.attr.query.AttrPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
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
 * 商品属性 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Tag(name = "商品属性")
@Validated
@RestController
@RequestMapping("/v1/attr")
@RequiredArgsConstructor
public class AttrController extends BaseController {

    private final AttrCommandHandler attrCommandHandler;
    private final AttrQueryService attrQueryService;

    @Operation(summary = "创建商品属性")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated AttrCreateCmd cmd) {
        return SingleResponse.ok(attrCommandHandler.saveAttr(cmd));
    }

    @Operation(summary = "删除商品属性")
    @DeleteMapping("/remove")
    public ServerResponse remove(@RequestParam Long id) {
        attrCommandHandler.removeByAttrId(id);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询商品属性分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<AttrDTO>> queryPages(@RequestBody @Validated AttrPageQry queryDTO) {
        return SingleResponse.ok(attrQueryService.queryAttrTemplatePages(queryDTO));
    }

    @Operation(summary = "查询商品属性详情")
    @GetMapping("/details")
    public SingleResponse<AttrDTO> queryAttrDetails(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrQueryService.queryAttrDetails(id));
    }

}
