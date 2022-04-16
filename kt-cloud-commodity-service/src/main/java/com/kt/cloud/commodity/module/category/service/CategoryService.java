package com.kt.cloud.commodity.module.category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.CategoryDO;
import com.kt.cloud.commodity.dao.mapper.CategoryMapper;
import com.kt.cloud.commodity.module.category.dto.request.CategoryPageQueryReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryUpdateReqDTO;
import com.kt.cloud.commodity.module.category.dto.response.CategoryRespDTO;
import com.kt.component.dto.PageResponse;
import com.kt.component.orm.mybatis.base.BaseEntity;
import com.kt.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryDO> implements IService<CategoryDO> {

    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryUpdateReqDTO reqDTO) {
        CategoryDO entity = BeanConvertor.copy(reqDTO, CategoryDO.class);
        entity.setId(null);
        save(entity);
        // 更新分类级别路径
        updateLevelAndLevelPath(entity);
        return entity.getId();
    }

    private void updateLevelAndLevelPath(CategoryDO entity) {
        String levelPath = "";
        int level = 1;
        if (entity.getPid() == null || entity.getPid() == 0) {
            levelPath = entity.getId() + ".";
        } else {
            CategoryDO parent = getById(entity.getPid());
            levelPath = parent.getLevelPath() + entity.getId() + ".";
            level = parent.getLevel() + 1;
        }
        CategoryDO updateEntity = new CategoryDO();
        updateEntity.setId(entity.getId());
        updateEntity.setLevelPath(levelPath);
        updateEntity.setLevel(level);
        updateById(updateEntity);
    }

    public PageResponse<CategoryRespDTO> getPageList(CategoryPageQueryReqDTO queryDTO) {
        IPage<CategoryRespDTO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CategoryRespDTO.class));
        return BeanConvertor.copyPage(page, CategoryRespDTO.class);
    }

    public Long updateCategory(CategoryUpdateReqDTO reqDTO) {
        CategoryDO entity = BeanConvertor.copy(reqDTO, CategoryDO.class);
        updateById(entity);
        return entity.getId();
    }

    public CategoryRespDTO getCategoryInfo(Long CategoryId) {
        CategoryDO entity = getById(CategoryId);
        return BeanConvertor.copy(entity, CategoryRespDTO.class);
    }

}
