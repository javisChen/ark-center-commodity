package com.ark.center.commodity.infra.category.convertor;

import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.infra.category.repository.db.CategoryDO;
import com.ark.ddd.base.RepositoryConvertor;
import org.springframework.stereotype.Component;

@Component
public class CategoryConvertor extends RepositoryConvertor<Category, CategoryDO> {


}
