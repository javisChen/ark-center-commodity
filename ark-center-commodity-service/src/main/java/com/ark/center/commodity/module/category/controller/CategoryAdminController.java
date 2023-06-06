//package com.ark.center.commodity.module.category.controller;
//
//import com.ark.center.commodity.module.category.dto.request.CategoryPageQueryReqDTO;
//import com.ark.center.commodity.module.category.dto.request.CategoryUpdateReqDTO;
//import com.ark.center.commodity.module.category.dto.response.CategoryRespDTO;
//import com.ark.center.commodity.module.category.dto.response.TreeDTO;
//import com.ark.center.commodity.module.category.service.CategoryAdminService;
//import com.ark.component.dto.PageResponse;
//import com.ark.component.dto.ServerResponse;
//import com.ark.component.dto.SingleResponse;
//import com.ark.component.validator.ValidateGroup;
//import com.ark.component.web.base.BaseController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.constraints.NotNull;
//
///**
// * <p>
// * 商品类目 前端控制器
// * </p>
// *
// * @author EOP
// * @since 2022-03-03
// */
//@Api(tags = "管理端-商品类目")
//@Validated
//@RestController
//@RequestMapping("/v1/admin/category")
//@RequiredArgsConstructor
//@Slf4j
//public class CategoryAdminController extends BaseController {
//    private final CategoryAdminService categoryAdminService;
//
//    @ApiOperation(value = "创建商品类目")
//    @PostMapping("/create")
//    public SingleResponse<Long> create(@RequestBody @Validated CategoryUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(categoryAdminService.createCategory(reqDTO));
//    }
//
//    @ApiOperation(value = "修改商品类目")
//    @PostMapping("/update")
//    public SingleResponse<Long> update(@RequestBody @Validated(ValidateGroup.Update.class) CategoryUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(categoryAdminService.updateCategory(reqDTO));
//    }
//    @ApiOperation(value = "查询商品类目分页列表")
//    @PostMapping("/page")
//    public SingleResponse<PageResponse<CategoryRespDTO>> pageList(@RequestBody @Validated CategoryPageQueryReqDTO queryDTO) {
//        return SingleResponse.ok(categoryAdminService.getPageList(queryDTO));
//    }
//
//    @ApiOperation(value = "查询商品类目树形结构")
//    @PostMapping("/tree")
//    public SingleResponse<TreeDTO<CategoryRespDTO>> tree(@RequestBody @Validated CategoryPageQueryReqDTO queryDTO) {
//        return SingleResponse.ok(categoryAdminService.getTree(queryDTO));
//    }
//
//    @ApiOperation(value = "删除类目（包括其下所有子分类）")
//    @DeleteMapping
//    public ServerResponse remove(@RequestParam Long id) {
//        categoryAdminService.removeCategoryById(id);
//        return ServerResponse.ok();
//    }
//
//    @ApiOperation(value = "查询商品类目详情")
//    @ApiImplicitParam(name = "id", value = "id", required = true)
//    @GetMapping("/info")
//    public SingleResponse<CategoryRespDTO> info(@RequestParam(required = false) @NotNull(message = "id不能为空") Long id) {
//        return SingleResponse.ok(categoryAdminService.getCategoryInfo(id));
//    }
//
//
//}
