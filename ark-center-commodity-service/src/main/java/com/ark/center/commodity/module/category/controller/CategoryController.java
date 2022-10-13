package com.ark.center.commodity.module.category.controller;

import com.ark.center.commodity.module.category.dto.response.HomeCategoryDTO;
import com.ark.center.commodity.module.category.service.CategoryAppService;
import com.ark.component.dto.MultiResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户端-商品类目")
@Validated
@RestController
@RequestMapping("/v1")
@Slf4j
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryAppService categoryAppService;

    @ApiOperation(value = "查看首页展示商品类目")
    @GetMapping("/categories")
    public MultiResponse<HomeCategoryDTO> categories() {
        return MultiResponse.ok(categoryAppService.getCategories());
    }

}
