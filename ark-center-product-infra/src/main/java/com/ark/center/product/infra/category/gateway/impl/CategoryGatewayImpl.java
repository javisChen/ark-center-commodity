package com.ark.center.product.infra.category.gateway.impl;

import com.ark.center.product.client.category.dto.CategoryDTO;
import com.ark.center.product.client.category.query.CategoryPageQry;
import com.ark.center.product.domain.category.gateway.CategoryGateway;
import com.ark.center.product.infra.category.convertor.CategoryConvertor;
import com.ark.center.product.domain.category.Category;
import com.ark.center.product.infra.category.gateway.db.CategoryMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CategoryGatewayImpl extends ServiceImpl<CategoryMapper, Category> implements IService<Category>, CategoryGateway {

    private final CategoryConvertor categoryConvertor;

    @Override
    public IPage<CategoryDTO> selectPages(CategoryPageQry qry) {
        return lambdaQuery()
                .eq(Objects.nonNull(qry.getLevel()), Category::getLevel, qry.getLevel())
                .eq(Objects.nonNull(qry.getPid()), Category::getPid, qry.getPid())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(new Page<>(qry.getCurrent(), qry.getSize()))
                .convert(categoryConvertor::toDTO);
    }

    @Override
    public Long insert(Category category) {
        save(category);
        return category.getId();
    }

    @Override
    public Category selectById(Long categoryId) {
        return lambdaQuery()
                .eq(BaseEntity::getId, categoryId)
                .one();
    }

    @Override
    public boolean update(Category category) {
        return updateById(category);
    }

    @Override
    public List<CategoryDTO> selectList(CategoryPageQry qry) {
        List<Category> records = lambdaQuery()
                .eq(Objects.nonNull(qry.getLevel()), Category::getLevel, qry.getLevel())
                .eq(Objects.nonNull(qry.getPid()), Category::getPid, qry.getPid())
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        return categoryConvertor.toDTO(records);
    }

    @Override
    public boolean remove(Long categoryId) {
        Category category = getById(categoryId);
        if (category != null) {
            return lambdaUpdate()
                    .likeRight(Category::getLevelPath, category.getId())
                    .remove();
        }
        return false;
    }

    @Override
    public List<Category> selectByIds(List<Long> ids) {
        return lambdaQuery()
                .in(BaseEntity::getId, ids)
                .list();
    }

    @Override
    public List<Category> selectByLevelPath(String levelPath) {
        return lambdaQuery()
                .likeRight(Category::getLevelPath, levelPath)
                .list();
    }

}
