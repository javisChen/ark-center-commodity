package com.kt.cloud.commodity.module.category.service;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.kt.cloud.commodity.dao.entity.CategoryDO;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.module.category.dto.response.HomeCategoryDTO;
import com.kt.cloud.commodity.module.commodity.service.SpuService;
import com.kt.component.orm.mybatis.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Service
public class CategoryAppService {

    private final SpuService spuService;
    private final CategoryAdminService categoryAdminService;

    public CategoryAppService(SpuService spuService, CategoryAdminService categoryAdminService) {
        this.spuService = spuService;
        this.categoryAdminService = categoryAdminService;
    }

    public List<HomeCategoryDTO> getCategories() {
        List<CategoryDO> categoryDOS = categoryAdminService.list();

        List<Long> categoryIds = categoryDOS.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        Map<Long, List<SpuDO>> spuMap = spuService.listByCategoryIds(categoryIds)
                .stream()
                .collect(Collectors.groupingBy(SpuDO::getCategoryId));

        List<HomeCategoryDTO> resultList = Lists.newArrayListWithCapacity(categoryDOS.size());
        for (CategoryDO categoryDO : categoryDOS) {
            HomeCategoryDTO homeCategoryDTO = new HomeCategoryDTO();
            homeCategoryDTO.setCategoryId(categoryDO.getId());
            homeCategoryDTO.setCategoryName(categoryDO.getName());
            List<SpuDO> spuDOList = spuMap.get(categoryDO.getId());
            if (CollectionUtil.isNotEmpty(spuDOList)) {
                List<HomeCategoryDTO.SubCommodity> commodities = Lists.newArrayListWithCapacity(spuDOList.size());
                for (SpuDO spuDO : spuDOList) {
                    HomeCategoryDTO.SubCommodity subCommodity = new HomeCategoryDTO.SubCommodity();
                    subCommodity.setSpuId(spuDO.getId());
                    subCommodity.setSpuName(spuDO.getName());
                    subCommodity.setPicUrl(spuDO.getMainPicture());
                    commodities.add(subCommodity);
                }
                homeCategoryDTO.setCommodities(commodities);
            }
            resultList.add(homeCategoryDTO);
        }
        return resultList;
    }
}
