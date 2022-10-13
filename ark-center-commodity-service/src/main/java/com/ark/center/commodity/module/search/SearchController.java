package com.ark.center.commodity.module.search;

import com.ark.component.web.base.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
