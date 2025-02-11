package com.ark.center.product.adapter.category.web;

import com.ark.center.product.app.category.service.CategoryAdminAppService;
import com.ark.center.product.client.category.command.CategoryCreateCmd;
import com.ark.center.product.client.category.dto.CategoryDTO;
import com.ark.center.product.client.category.dto.TreeDTO;
import com.ark.center.product.client.category.query.CategoryPageQry;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Tags({
        @Tag(name = "商品分类服务", description = "商品分类服务"),
})
@Validated
@RestController
@RequestMapping("/v1/admin/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController extends BaseController {

    private final CategoryAdminAppService categoryAdminAppService;

    @Operation(summary = "创建商品分类")
    @PostMapping("/save")
    public SingleResponse<Long> save(@RequestBody @Validated CategoryCreateCmd command) {
        return SingleResponse.ok(categoryAdminAppService.save(command));
    }

    @Operation(summary = "查询商品分类分页列表")
    @PostMapping("/pages")
    public SingleResponse<PageResponse<CategoryDTO>> queryPages(@RequestBody @Validated CategoryPageQry queryDTO) {
        return SingleResponse.ok(categoryAdminAppService.queryPages(queryDTO));
    }

    @Operation(summary = "查询商品分类树形结构")
    @PostMapping("/tree")
    public SingleResponse<TreeDTO<CategoryDTO>> tree(@RequestBody @Validated CategoryPageQry queryDTO) {
        return SingleResponse.ok(categoryAdminAppService.getTree(queryDTO));
    }

    @Operation(summary = "删除分类（包括其下所有子分类）")
    @DeleteMapping("/delete")
    public ServerResponse remove(@RequestParam Long id) {
        categoryAdminAppService.removeCategoryById(id);
        return ServerResponse.ok();
    }

    @Operation(summary = "查询商品分类详情")
    @GetMapping("/details")
    public SingleResponse<CategoryDTO> queryDetails(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(categoryAdminAppService.queryDetails(id));
    }


}
