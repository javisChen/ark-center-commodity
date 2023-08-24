package com.ark.center.commodity.adapter.attr.web;

import com.ark.center.commodity.app.attr.service.AttrApplicationService;
import com.ark.center.commodity.client.attr.command.AttrGroupCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrGroupUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @PostMapping("/create")
    public SingleResponse<Long> createGroup(@RequestBody @Validated AttrGroupCreateCmd reqDTO) {
        return SingleResponse.ok(attrService.createAttrGroup(reqDTO));
    }

    @Operation(summary = "修改商品属性组")
    @PostMapping("/update")
    public ServerResponse updateGroup(@RequestBody @Validated AttrGroupUpdateCmd reqDTO) {
        attrService.updateAttrGroup(reqDTO);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询商品属性组分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<AttrGroupDTO>> getGroupPageList(@RequestBody @Validated AttrGroupPageQry queryDTO) {
        return SingleResponse.ok(attrService.getAttrGroupPageList(queryDTO));
    }

    @Operation(summary = "查询商品属性组详情")
    @GetMapping("/info")
    public SingleResponse<AttrGroupDTO> getAttrGroupInfo(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(attrService.getAttrGroupInfo(id));
    }

}
