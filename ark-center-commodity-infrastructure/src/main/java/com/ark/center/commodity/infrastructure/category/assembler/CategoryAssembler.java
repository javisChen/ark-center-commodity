package com.ark.center.commodity.infrastructure.category.assembler;

import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.ProductCategory;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Component;


/**
 * 售后补偿数据转换工具类
 *
 * @author wl
 * @date 2021/8/26
 */

@Component
public class CategoryAssembler {

    public PageResponse<CategoryDTO> pageEntityToDTO(PageResponse<ProductCategory> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }

    public PageResponse<CategoryDTO> cmdToEntity(PageResponse<ProductCategory> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }

}














