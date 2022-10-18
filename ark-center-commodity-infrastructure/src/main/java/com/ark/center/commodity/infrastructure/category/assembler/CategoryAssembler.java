package com.ark.center.commodity.infrastructure.category.assembler;

import com.ark.center.commodity.client.category.command.CategoryUpdateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.infrastructure.db.dataobject.CategoryDO;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 售后补偿数据转换工具类
 *
 * @author wl
 * @date 2021/8/26
 */

@Component
public class CategoryAssembler {

    public PageResponse<CategoryDTO> entityToDTO(PageResponse<Category> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }
    public List<CategoryDTO> entityToDTO(List<Category> list) {
        return BeanConvertor.copyList(list, CategoryDTO.class);
    }

    public PageResponse<CategoryDTO> cmdToEntity(PageResponse<Category> page) {
        return BeanConvertor.copyPage(page, CategoryDTO.class);
    }

    public PageResponse<Category> doToEntity(IPage<CategoryDO> page) {
        return BeanConvertor.copyPage(page, Category.class);
    }

    public List<Category> doToEntity(List<CategoryDO> page) {
        return BeanConvertor.copyList(page, Category.class);
    }

    public CategoryDO entityToDO(Category category) {
        return BeanConvertor.copy(category, CategoryDO.class);
    }


    public Category updateCmdToCategory(CategoryUpdateCommand command) {
        return BeanConvertor.copy(command, Category.class);
    }

    public CategoryDTO entityToDTO(Category category) {
        return BeanConvertor.copy(category, CategoryDTO.class);
    }
}














