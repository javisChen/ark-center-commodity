package com.ark.center.commodity.domain.category.repository;

import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.component.dto.PageResponse;

import java.util.List;

public interface CategoryRepository {
    PageResponse<Category> pageList(CategoryPageQuery queryDTO);
    Long save(Category category);

    Category findById(Long categoryId);

    boolean update(Category category);

    List<Category> list(CategoryPageQuery queryDTO);

    void removeCategory(Long categoryId);
}
