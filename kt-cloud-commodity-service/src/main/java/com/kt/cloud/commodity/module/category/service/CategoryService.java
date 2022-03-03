package com.kt.cloud.commodity.module.category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.CategoryDO;
import com.kt.cloud.commodity.dao.mapper.CategoryMapper;
import com.kt.cloud.commodity.module.category.dto.request.CategoryCreateReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryUpdateReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryPageQueryReqDTO;
import com.kt.cloud.commodity.module.category.dto.response.CategoryRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

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

    public Long createCategory(CategoryCreateReqDTO reqDTO) {
        CategoryDO entity = BeanConvertor.copy(reqDTO, CategoryDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<CategoryRespDTO> getPageList(CategoryPageQueryReqDTO queryDTO) {
        IPage<CategoryRespDTO> page = lambdaQuery()
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
