package com.ark.center.commodity.infrastructure.category.assembler;

import com.ark.center.commodity.client.category.command.CategoryCreateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.infrastructure.db.dataobject.CategoryDO;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Component;


/**
 * 售后补偿数据转换工具类
 *
 * @author wl
 * @date 2021/8/26
 */

@Component
public class CategoryAssembler {

    public PageResponse<CategoryDTO> pageEntityToDTO(PageResponse<Category> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }

    public PageResponse<CategoryDTO> cmdToEntity(PageResponse<Category> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }

    public CategoryDO entityToDO(Category category) {
        return BeanConvertor.copy(category, CategoryDO.class);
    }

    public Category dtoToProductCategory(CategoryCreateCommand createCommand) {
        return BeanConvertor.copy(createCommand, Category.class);
    }
}














