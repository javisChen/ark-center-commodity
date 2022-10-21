package com.ark.center.commodity.domain.category.assembler;

import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.client.category.command.CategoryUpdateCmd;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryAssembler {

    public PageResponse<CategoryDTO> toDTO(PageResponse<Category> page) {
        return page.convert(item -> BeanConvertor.copy(item, CategoryDTO.class));
    }

    public List<CategoryDTO> toDTO(List<Category> list) {
        return BeanConvertor.copyList(list, CategoryDTO.class);
    }

    public Category updateCmdToCategory(CategoryUpdateCmd command) {
        return BeanConvertor.copy(command, Category.class);
    }

    public Category createCmdToCategory(CategoryCreateCmd command) {
        return BeanConvertor.copy(command, Category.class);
    }

    public CategoryDTO toDTO(Category category) {
        return BeanConvertor.copy(category, CategoryDTO.class);
    }
}














