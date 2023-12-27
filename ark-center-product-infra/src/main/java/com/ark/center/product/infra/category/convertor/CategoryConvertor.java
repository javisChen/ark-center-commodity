package com.ark.center.product.infra.category.convertor;

import com.ark.center.product.client.category.command.CategoryCreateCmd;
import com.ark.center.product.client.category.command.CategoryUpdateCmd;
import com.ark.center.product.client.category.dto.CategoryDTO;
import com.ark.center.product.domain.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryConvertor {

    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "levelPath", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "code", ignore = true)
    Category toCategory(CategoryUpdateCmd command);

    Category toCategory(CategoryCreateCmd command);

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTO(List<Category> categories);
}
