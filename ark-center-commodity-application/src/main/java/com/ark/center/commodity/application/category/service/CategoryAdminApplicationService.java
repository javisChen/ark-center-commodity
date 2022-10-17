package com.ark.center.commodity.application.category.service;

import com.ark.center.commodity.client.category.command.CategoryCreateCommand;
import com.ark.center.commodity.client.category.command.CategoryUpdateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.factory.CategoryFactory;
import com.ark.center.commodity.domain.category.gateway.CategoryGateway;
import com.ark.center.commodity.infrastructure.category.assembler.CategoryAssembler;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryAdminApplicationService {

    private final CategoryGateway categoryGateway;
    private final CategoryFactory categoryFactory;
    private final CategoryAssembler assembler;;

    public PageResponse<CategoryDTO> pageList(CategoryPageQuery queryDTO) {
        PageResponse<Category> page = categoryGateway.pageList(queryDTO);
        return assembler.pageEntityToDTO(page);
    }

    public Long createCategory(CategoryCreateCommand command) {
        Category category = categoryFactory.createCategory(command);
        return categoryGateway.save(category);
    }

    public Long updateCategory(CategoryUpdateCommand reqDTO) {
        return null;
    }

}
