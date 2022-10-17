package com.ark.center.commodity.domain.category.gateway;

import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.component.dto.PageResponse;

public interface CategoryGateway {


    PageResponse<Category> pageList(CategoryPageQuery queryDTO);

    Long save(Category category);
}
