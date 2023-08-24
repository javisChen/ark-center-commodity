package com.ark.center.commodity.adapter.attr.web;

import com.ark.center.commodity.app.attr.service.AttrApplicationService;
import com.ark.center.commodity.client.attr.command.AttrSaveCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.validator.ValidateGroup;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Api(tags = "商品属性")
@Validated
@RestController
@RequestMapping("/v1/attr")
@RequiredArgsConstructor
public class AttrController extends BaseController {

    private final AttrApplicationService attrService;

    @Operation(summary = "创建商品属性")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated AttrSaveCmd reqDTO) {
        return SingleResponse.ok(attrService.createAttr(reqDTO));
    }

    @Operation(summary = "修改商品属性")
    @PostMapping("/update")
    public ServerResponse update(@RequestBody @Validated(ValidateGroup.Update.class) AttrSaveCmd reqDTO) {
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
    public SingleResponse<PageResponse<AttrDTO>> pageList(@RequestBody @Validated AttrPageQry queryDTO) {
        return SingleResponse.ok(attrService.getAttrPageList(queryDTO));
    }

    @Operation(summary = "查询商品属性详情")
    @GetMapping("/info")
    public SingleResponse<AttrDTO> getInfo(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.getAttrInfo(id));
    }

}
