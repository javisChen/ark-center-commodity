package com.ark.center.commodity.infrastructure.category.gateway.impl;

import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.repository.CategoryRepository;
import com.ark.center.commodity.domain.category.assembler.CategoryAssembler;
import com.ark.center.commodity.infrastructure.db.dataobject.CategoryDO;
import com.ark.center.commodity.infrastructure.db.mapper.CategoryMapper;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
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
public class CategoryRepositoryImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements IService<CategoryDO>, CategoryRepository {

    private final CategoryAssembler assembler;

    @Override
    public PageResponse<Category> pageList(CategoryPageQuery queryDTO) {
        IPage<CategoryDO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        return assembler.doToEntity(page);
    }

    @Override
    public Long save(Category category) {
        CategoryDO db = assembler.entityToDO(category);
        save(db);
        return db.getId();
    }

    @Override
    public Category findById(Long categoryId) {
        CategoryDO one = lambdaQuery()
                .eq(BaseEntity::getId, categoryId)
                .one();
        return BeanConvertor.copy(one, Category.class);
    }

    @Override
    public boolean update(Category category) {
        CategoryDO db = assembler.entityToDO(category);
        return updateById(db);
    }

    @Override
    public List<Category> list(CategoryPageQuery queryDTO) {
        List<CategoryDO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .list();
        return assembler.doToEntity(page);
    }

    @Override
    public void removeCategory(Long categoryId) {
        CategoryDO categoryDO = getById(categoryId);
        if (categoryDO != null) {
            lambdaUpdate()
                    .likeRight(CategoryDO::getLevelPath, categoryDO.getId())
                    .remove();
        }
    }

}
