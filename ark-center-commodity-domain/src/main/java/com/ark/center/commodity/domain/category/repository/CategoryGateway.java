package com.ark.center.commodity.domain.category.repository;

import com.ark.center.commodity.client.category.query.CategoryPageQry;
import com.ark.center.commodity.domain.category.Category;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CategoryGateway {
    IPage<Category> selectPages(CategoryPageQry qry);

    Long insert(Category category);

    Category selectById(Long categoryId);

    boolean update(Category category);

    List<Category> selectList(CategoryPageQry qry);

    boolean remove(Long categoryId);

    List<Category> selectByIds(List<Long> ids);
}
