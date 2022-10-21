package com.ark.center.commodity.domain.category.factory;

import cn.hutool.core.util.RandomUtil;
import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.assembler.CategoryAssembler;
import com.ark.center.commodity.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryAssembler categoryAssembler;
    private final CategoryRepository categoryRepository;

    public Category create(CategoryCreateCmd createCommand) {
        Category category = categoryAssembler.createCmdToCategory(createCommand);
        String categoryCode = generateCategoryCode();
        String levelPath;
        int level = 1;
        if (category.isParentCategory()) {
            levelPath = categoryCode + ".";
        } else {
            Category parentCategory = categoryRepository.findById(category.getPid());
            levelPath = parentCategory.getLevelPath() + categoryCode + ".";
            level = parentCategory.getLevel() + 1;
        }
        category.setCode(categoryCode);
        category.setLevel(level);
        category.setLevelPath(levelPath);
        return category;
    }

    private String generateCategoryCode() {
        return RandomUtil.randomString(8);
    }

}
