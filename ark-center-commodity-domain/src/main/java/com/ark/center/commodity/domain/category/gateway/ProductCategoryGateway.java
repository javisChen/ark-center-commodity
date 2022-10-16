package com.ark.center.commodity.domain.category.gateway;

import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.ProductCategory;
import com.ark.component.dto.PageResponse;

public interface ProductCategoryGateway {


    PageResponse<ProductCategory> pageList(CategoryPageQuery queryDTO);
}
