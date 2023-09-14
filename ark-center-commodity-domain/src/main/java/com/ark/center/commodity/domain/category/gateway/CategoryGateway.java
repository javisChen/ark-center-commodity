package com.ark.center.commodity.domain.category.gateway;

import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQry;
import com.ark.center.commodity.domain.category.Category;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CategoryGateway {
    IPage<CategoryDTO> selectPages(CategoryPageQry qry);

    Long insert(Category category);

    Category selectById(Long categoryId);

    boolean update(Category category);

    List<CategoryDTO> selectList(CategoryPageQry qry);

    boolean remove(Long categoryId);

    List<Category> selectByIds(List<Long> ids);
}
