package com.kt.cloud.commodity.module.category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.Category;
import com.kt.cloud.commodity.dao.mapper.CategoryMapper;
import com.kt.cloud.commodity.module.category.dto.request.CategoryCreateReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryUpdateReqDTO;
import com.kt.cloud.commodity.module.category.dto.request.CategoryPageQueryReqDTO;
import com.kt.cloud.commodity.module.category.dto.response.CategoryRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-02
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> implements IService<Category> {

    public Long createCategory(CategoryCreateReqDTO reqDTO) {
            Category entity = BeanConvertor.copy(reqDTO, Category.class);
            save(entity);
            return entity.getId();
        }

    public IPage<CategoryRespDTO> getPageList(CategoryPageQueryReqDTO queryDTO) {
        return lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CategoryRespDTO.class));
    }

    public Long updateCategory(CategoryUpdateReqDTO reqDTO) {
        Category entity = BeanConvertor.copy(reqDTO, Category.class);
        updateById(entity);
        return entity.getId();
    }

    public CategoryRespDTO getCategoryInfo(Long CategoryId) {
        Category Category = getById(CategoryId);
        return BeanConvertor.copy(Category, CategoryRespDTO.class);
    }

}
