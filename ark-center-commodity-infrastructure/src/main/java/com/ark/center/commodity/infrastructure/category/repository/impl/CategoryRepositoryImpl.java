package com.ark.center.commodity.infrastructure.category.repository.impl;

import com.ark.center.commodity.client.category.query.CategoryPageQry;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.repository.CategoryRepository;
import com.ark.center.commodity.infrastructure.category.convertor.CategoryConvertor;
import com.ark.center.commodity.infrastructure.category.repository.db.CategoryDO;
import com.ark.center.commodity.infrastructure.category.repository.db.CategoryMapper;
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
public class CategoryRepositoryImpl extends ServiceImpl<CategoryMapper, CategoryDO>
        implements IService<CategoryDO>, CategoryRepository {

    private final CategoryConvertor categoryConvertor;

    @Override
    public IPage<Category> pageList(CategoryPageQry queryDTO) {
        IPage<CategoryDO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        return categoryConvertor.toAggregate(page);
    }

    @Override
    public Long store(Category category) {
        CategoryDO db = categoryConvertor.fromDomain(category);
        save(db);
        return db.getId();
    }

    @Override
    public Category findById(Long categoryId) {
        CategoryDO one = lambdaQuery()
                .eq(BaseEntity::getId, categoryId)
                .one();
        return categoryConvertor.toAggregate(one);
    }

    @Override
    public boolean update(Category category) {
        CategoryDO db = categoryConvertor.fromDomain(category);
        return updateById(db);
    }

    @Override
    public List<Category> list(CategoryPageQry queryDTO) {
        List<CategoryDO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .list();
        return categoryConvertor.toAggregate(page);
    }

    @Override
    public boolean remove(Long categoryId) {
        CategoryDO categoryDO = getById(categoryId);
        if (categoryDO != null) {
            return lambdaUpdate()
                    .likeRight(CategoryDO::getLevelPath, categoryDO.getCode())
                    .remove();
        }
        return false;
    }

}
