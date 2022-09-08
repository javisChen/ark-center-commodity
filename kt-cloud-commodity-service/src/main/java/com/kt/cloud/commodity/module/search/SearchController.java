package com.kt.cloud.commodity.module.search;

import com.kt.cloud.commodity.module.category.dto.request.CategoryUpdateReqDTO;
import com.kt.cloud.commodity.module.category.service.CategoryService;
import com.kt.component.dto.MultiResponse;
import com.kt.component.dto.SingleResponse;
import com.kt.component.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "搜索")
@Validated
@RestController
@RequestMapping("/v1/search")
@Slf4j
public class SearchController extends BaseController {

//    private final SearchService searchService;
//
//    public SearchController(SearchService searchService) {
//        this.searchService = searchService;
//    }
//    @ApiOperation(value = "类目搜索")
//    @PostMapping("/category")
//    public MultiResponse<Long> create(@RequestBody @Validated CategoryUpdateReqDTO reqDTO) {
//        return SingleResponse.ok(searchService.search(reqDTO));
//    }
}
