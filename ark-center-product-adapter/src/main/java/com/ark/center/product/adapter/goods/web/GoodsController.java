package com.ark.center.product.adapter.goods.web;

import com.ark.center.product.app.goods.service.GoodsAppService;
import com.ark.center.product.client.category.dto.HomeCategoryDTO;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.search.dto.SearchResultDTO;
import com.ark.center.product.client.search.query.SearchQry;
import com.ark.component.dto.MultiResponse;
import com.ark.component.dto.SingleResponse;
import com.ark.component.web.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * sku 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Tag(name = "商品接口（C端）")
@RestController
@RequestMapping("/v1/goods")
@RequiredArgsConstructor
public class GoodsController extends BaseController {

    private final GoodsAppService goodsAppService;

    @Operation(summary = "查询分页列表")
    @GetMapping("/search")
    public SingleResponse<SearchResultDTO> search(SearchQry searchQry) {
        SearchResultDTO search = goodsAppService.search(searchQry);
        return SingleResponse.ok(search);
    }

    @Operation(summary = "查询首页分类")
    @GetMapping("/categories")
    public MultiResponse<HomeCategoryDTO> homeCategories() {
        List<HomeCategoryDTO> search = goodsAppService.queryHomeCategories();
        return MultiResponse.ok(search);
    }

    @Operation(summary = "查询详情")
    @GetMapping("/details")
    public SingleResponse<GoodsDTO> details(@RequestParam(required = false)
                                            @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(goodsAppService.queryDetails(id));
    }

}
