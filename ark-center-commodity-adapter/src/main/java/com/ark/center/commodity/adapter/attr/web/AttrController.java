package com.ark.center.commodity.adapter.attr.web;

import com.ark.center.commodity.app.attr.AttrApplicationService;
import com.ark.center.commodity.client.attr.command.AttrCreateCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.validator.ValidateGroup;
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

    private final AttrApplicationService attrService;

    @Operation(summary = "创建商品属性")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrCreateCmd cmd) {
        return SingleResponse.ok(attrService.createAttr(cmd));
    }

    @Operation(summary = "修改商品属性")
    @PostMapping("/update")
    public ServerResponse update(@RequestBody @Validated(ValidateGroup.Update.class) AttrCreateCmd reqDTO) {
        attrService.updateAttr(reqDTO);
        return ServerResponse.ok();
    }

    @Operation(summary = "删除商品属性")
    @DeleteMapping
    public ServerResponse remove(@RequestParam Long id) {
        attrService.removeByAttrId(id);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询商品属性分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrDTO>> queryPages(@RequestBody @Validated AttrPageQry queryDTO) {
        return SingleResponse.ok(attrService.queryPages(queryDTO));
    }

    @Operation(summary = "查询商品属性详情")
    @GetMapping("/info")
    public SingleResponse<AttrDTO> getDetails(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.getDetails(id));
    }

}
