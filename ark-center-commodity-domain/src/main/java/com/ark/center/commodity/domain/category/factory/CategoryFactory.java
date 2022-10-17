package com.ark.center.commodity.domain.category.factory;

import com.ark.center.commodity.client.category.command.CategoryCreateCommand;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.infrastructure.category.assembler.CategoryAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryAssembler assembler;

    public Category createCategory(CategoryCreateCommand createCommand) {
        return assembler.dtoToProductCategory(createCommand);
    }

}
