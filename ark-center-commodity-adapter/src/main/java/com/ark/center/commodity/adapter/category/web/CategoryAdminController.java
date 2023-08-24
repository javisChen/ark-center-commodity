package com.ark.center.commodity.adapter.category.web;

import com.ark.center.commodity.app.category.service.CategoryAdminAppService;
import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.client.category.command.CategoryUpdateCmd;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.dto.TreeDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQry;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Api(tags = "商品类目接口")
@Validated
@RestController
@RequestMapping("/v1/admin/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController extends BaseController {
    private final CategoryAdminAppService categoryAdminAppService;

    @Operation(summary = "创建商品类目")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated CategoryCreateCmd command) {
        return SingleResponse.ok(categoryAdminAppService.createCategory(command));
    }

    @Operation(summary = "修改商品类目")
    @PostMapping("/update")
    public ServerResponse update(@RequestBody @Validated(ValidateGroup.Update.class) CategoryUpdateCmd reqDTO) {
        categoryAdminAppService.updateCategory(reqDTO);
        return ServerResponse.ok();
    }
    @Operation(summary = "查询商品类目分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<CategoryDTO>> pageList(@RequestBody @Validated CategoryPageQry queryDTO) {
        return SingleResponse.ok(categoryAdminAppService.pageList(queryDTO));
    }

    @Operation(summary = "查询商品类目树形结构")
    @PostMapping("/tree")
    public SingleResponse<TreeDTO<CategoryDTO>> tree(@RequestBody @Validated CategoryPageQry queryDTO) {
        return SingleResponse.ok(categoryAdminAppService.getTree(queryDTO));
    }

    @Operation(summary = "删除类目（包括其下所有子分类）")
    @DeleteMapping
    public ServerResponse remove(@RequestParam Long id) {
        categoryAdminAppService.removeCategoryById(id);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询商品类目详情")
    @GetMapping("/info")
    public SingleResponse<CategoryDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(categoryAdminAppService.getCategoryInfo(id));
    }


}
