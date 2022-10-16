package com.ark.center.commodity.adapter.category.web;

import com.ark.center.commodity.application.category.service.CategoryAdminApplicationService;
import com.ark.center.commodity.client.category.command.CategoryUpdateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.dto.TreeDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.ServerResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.validator.ValidateGroup;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Api(tags = "管理端-商品类目")
@Validated
@RestController
@RequestMapping("/v1/admin/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController extends BaseController {
    private final CategoryAdminApplicationService categoryAdminApplicationService;

    @ApiOperation(value = "创建商品类目")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated CategoryUpdateCommand command) {
        return SingleResponse.ok(categoryAdminApplicationService.createCategory(command));
    }

    @ApiOperation(value = "修改商品类目")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) CategoryUpdateCommand reqDTO) {
        return SingleResponse.ok(categoryAdminApplicationService.updateCategory(reqDTO));
    }
    @ApiOperation(value = "查询商品类目分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<CategoryDTO>> pageList(@RequestBody @Validated CategoryPageQuery queryDTO) {
        return SingleResponse.ok(categoryAdminApplicationService.pageList(queryDTO));
    }

    @ApiOperation(value = "查询商品类目树形结构")
    @PostMapping("/tree")
    public SingleResponse<TreeDTO<CategoryDTO>> tree(@RequestBody @Validated CategoryPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(categoryAdminApplicationService.getTree(queryDTO));
    }

    @ApiOperation(value = "删除类目（包括其下所有子分类）")
    @DeleteMapping
    public ServerResponse remove(@RequestParam Long id) {
        categoryAdminApplicationService.removeCategoryById(id);
        return ServerResponse.ok();
    }

    @ApiOperation(value = "查询商品类目详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<CategoryDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(categoryAdminApplicationService.getCategoryInfo(id));
    }


}
