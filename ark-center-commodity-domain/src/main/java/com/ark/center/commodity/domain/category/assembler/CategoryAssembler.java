package com.ark.center.commodity.domain.category.assembler;

import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.client.category.command.CategoryUpdateCmd;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryAssembler {

    Category toCategory(CategoryUpdateCmd command);

    Category toCategory(CategoryCreateCmd command);

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTO(List<Category> categories);

}














