package com.ark.center.commodity.domain.category.repository;

import com.ark.center.commodity.client.category.query.CategoryPageQry;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.ddd.base.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CategoryRepository extends Repository<Category, Long> {
    IPage<Category> pageList(CategoryPageQry queryDTO);

    List<Category> list(CategoryPageQry queryDTO);

}
