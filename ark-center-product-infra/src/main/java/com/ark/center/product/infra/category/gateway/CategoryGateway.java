package com.ark.center.product.infra.category.gateway;

import com.ark.center.product.client.category.dto.CategoryDTO;
import com.ark.center.product.client.category.query.CategoryPageQry;
import com.ark.center.product.infra.category.Category;
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

    List<Category> selectByLevelPath(String levelPath);
}
