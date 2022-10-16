package com.ark.center.commodity.application.category.service;

import com.ark.center.commodity.client.category.command.CategoryUpdateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.ProductCategory;
import com.ark.center.commodity.domain.category.gateway.ProductCategoryGateway;
import com.ark.center.commodity.infrastructure.category.assembler.CategoryAssembler;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryAdminApplicationService {

    private final ProductCategoryGateway productCategoryGateway;
    private final CategoryAssembler assembler;;

    public PageResponse<CategoryDTO> pageList(CategoryPageQuery queryDTO) {
        PageResponse<ProductCategory> page = productCategoryGateway.pageList(queryDTO);
        return assembler.pageEntityToDTO(page);
    }

    public Long createCategory(CategoryUpdateCommand command) {
        return null;
    }

    public Long updateCategory(CategoryUpdateCommand reqDTO) {
        return null;
    }

}
