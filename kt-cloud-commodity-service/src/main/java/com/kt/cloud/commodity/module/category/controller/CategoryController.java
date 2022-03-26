package com.kt.cloud.commodity.module.category.controller;

import com.kt.cloud.commodity.module.category.dto.request.CategoryCreateReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryPageQueryReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryUpdateReqDTO;
import com.kt.cloud.commodity.module.category.dto.response.CategoryRespDTO;
import com.kt.cloud.commodity.module.category.service.CategoryService;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "商品类目")
@Validated
@RestController
@RequestMapping("/v1/category")
@Slf4j
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "创建商品类目")
    @PostMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated CategoryCreateReqDTO reqDTO) {
        return SingleResponse.ok(categoryService.createCategory(reqDTO));
    }

    @ApiOperation(value = "修改商品类目")
    @PostMapping("/update")
    public SingleResponse<Long> update(@RequestBody @Validated CategoryUpdateReqDTO reqDTO) {
        return SingleResponse.ok(categoryService.updateCategory(reqDTO));
    }

    @ApiOperation(value = "查询商品类目分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<CategoryRespDTO>> pageList(@RequestBody @Validated CategoryPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(categoryService.getPageList(queryDTO));
    }

    @ApiOperation(value = "查询商品类目详情")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @GetMapping("/info")
    public SingleResponse<CategoryRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(categoryService.getCategoryInfo(id));
    }


}
