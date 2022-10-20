package com.ark.center.commodity.infrastructure.category.convertor;

import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.vo.CategoryCode;
import com.ark.center.commodity.domain.category.vo.CategoryId;
import com.ark.center.commodity.infrastructure.db.dataobject.CategoryDO;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryConvertor {

    public PageResponse<Category> doToEntity(IPage<CategoryDO> page) {
        IPage<Category> convert = page.convert(item -> {
            Category copy = BeanConvertor.copy(item, Category.class);
            copy.setId(new CategoryId(item.getId()));
            copy.setCode(new CategoryCode(item.getCode()));
            return copy;
        });
        return BeanConvertor.copyPage(convert, Category.class);
    }

    public List<Category> doToEntity(List<CategoryDO> page) {
        return BeanConvertor.copyList(page, Category.class);
    }

    public CategoryDO entityToDO(Category category) {
        return BeanConvertor.copy(category, CategoryDO.class);
    }


}
