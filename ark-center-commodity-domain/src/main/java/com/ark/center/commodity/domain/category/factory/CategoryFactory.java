package com.ark.center.commodity.domain.category.factory;

import cn.hutool.core.util.RandomUtil;
import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.domain.category.Category;
import com.ark.center.commodity.domain.category.assembler.CategoryAssembler;
import com.ark.center.commodity.domain.category.repository.CategoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFactory {

    private final CategoryAssembler categoryAssembler;
    private final CategoryGateway categoryGateway;

    public Category create(CategoryCreateCmd createCommand) {
        Category category = categoryAssembler.toCategory(createCommand);
        String categoryCode = generateCategoryCode();
        String levelPath;
        int level = 1;
        if (category.getPid() == null || category.getPid().equals(0L)) {
            levelPath = categoryCode + ".";
        } else {
            Category parentCategory = categoryGateway.selectById(category.getPid());
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
