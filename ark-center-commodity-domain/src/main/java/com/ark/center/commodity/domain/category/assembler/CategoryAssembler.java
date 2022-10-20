package com.ark.center.commodity.domain.category.assembler;

import com.ark.center.commodity.client.category.command.CategoryCreateCommand;
import com.ark.center.commodity.client.category.command.CategoryUpdateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 售后补偿数据转换工具类
 *
 * @author wl
 * @date 2021/8/26
 */

@Component
public class CategoryAssembler {

    public PageResponse<CategoryDTO> entityToDTO(PageResponse<Category> page) {
        return page.convert(item -> {
            CategoryDTO copy = BeanConvertor.copy(item, CategoryDTO.class);
            copy.setId(item.getId().getValue());
            return copy;
        });
    }

    public List<CategoryDTO> entityToDTO(List<Category> list) {
        return BeanConvertor.copyList(list, CategoryDTO.class);
    }

    public PageResponse<CategoryDTO> cmdToEntity(PageResponse<Category> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }

    public Category updateCmdToCategory(CategoryUpdateCommand command) {
        return BeanConvertor.copy(command, Category.class);
    }

    public Category createCmdToCategory(CategoryCreateCommand command) {
        return BeanConvertor.copy(command, Category.class);
    }

    public CategoryDTO entityToDTO(Category category) {
        return BeanConvertor.copy(category, CategoryDTO.class);
    }
}














