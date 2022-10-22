package com.ark.center.commodity.domain.category.assembler;

import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.client.category.command.CategoryUpdateCmd;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class CategoryAssembler extends BaseAssembler<Category, CategoryDTO> {

    public Category updateCmdToCategory(CategoryUpdateCmd command) {
        return BeanConvertor.copy(command, Category.class);
    }

    public Category createCmdToCategory(CategoryCreateCmd command) {
        return BeanConvertor.copy(command, Category.class);
    }

}














