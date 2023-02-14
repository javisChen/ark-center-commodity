package com.ark.center.commodity.app.commodity.service;

import com.ark.center.commodity.client.commodity.command.CommoditySaveCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.dto.SearchDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.center.commodity.domain.brand.repository.BrandRepository;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.repository.CategoryRepository;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.assembler.CommodityAssembler;
import com.ark.center.commodity.domain.commodity.factory.CommodityFactory;
import com.ark.center.commodity.domain.commodity.repository.CommodityRepository;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
@RequiredArgsConstructor
public class CommodityAppService {

    private final CommodityRepository commodityRepository;

    private final CommodityFactory commodityFactory;

    private final CommodityAssembler commodityAssembler;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long save(CommoditySaveCmd cmd) {
        Commodity commodity = commodityFactory.create(cmd);
        return commodityRepository.store(commodity);
    }

    public PageResponse<CommodityPageDTO> getPageList(CommodityPageQry queryDTO) {
        IPage<Commodity> pageList = commodityRepository.getPageList(queryDTO);
        List<Commodity> records = pageList.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return commodityAssembler.toPageResponse(pageList);
        }

        List<Long> brandIds = records.stream().map(Commodity::getBrandId).collect(Collectors.toList());
        List<Long> categoryIds = records.stream().map(Commodity::getCategoryId).collect(Collectors.toList());
        Map<Long, String> brandMap = brandRepository.queryByIds(brandIds).stream().collect(Collectors.toMap(Brand::getId, Brand::getName));
        Map<Long, String> categoryMap = categoryRepository.queryByIds(categoryIds).stream().collect(Collectors.toMap(Category::getId, Category::getName));

        return commodityAssembler.toPageResponse(pageList, brandMap, categoryMap);
    }

    public CommodityDTO getInfo(Long spuId) {

        Commodity commodity = commodityRepository.findById(spuId);
        CommodityDTO commodityDTO = commodityAssembler.toDTO(commodity);

        Category category = categoryRepository.findById(commodity.getCategoryId());
        commodityDTO.setCategoryLevelPath(category.getLevelPath());
        return commodityDTO;
    }

    public SearchDTO search() {
        return null;
    }
}
