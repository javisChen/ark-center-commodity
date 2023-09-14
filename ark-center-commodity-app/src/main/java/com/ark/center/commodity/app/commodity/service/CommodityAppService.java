package com.ark.center.commodity.app.commodity.service;

import com.ark.center.commodity.app.commodity.executor.CommodityCreateCmdExe;
import com.ark.center.commodity.app.commodity.executor.CommodityDetailsQryExe;
import com.ark.center.commodity.client.commodity.command.CommodityCreateCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.dto.SearchDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.brand.Brand;
import com.ark.center.commodity.domain.brand.gateway.BrandGateway;
import com.ark.center.commodity.domain.category.gateway.CategoryGateway;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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

    private final SpuGateway spuGateway;

    private final BrandGateway brandGateway;

    private final CategoryGateway categoryGateway;

    private final CommodityDetailsQryExe commodityDetailsQryExe;

    private final CommodityCreateCmdExe commodityCreateCmdExe;

    @Transactional(rollbackFor = Exception.class)
    public Long save(CommodityCreateCmd cmd) {
        return commodityCreateCmdExe.execute(cmd);
    }

    public PageResponse<CommodityPageDTO> queryPages(CommodityPageQry queryDTO) {
        IPage<CommodityPageDTO> pages = spuGateway.selectPages(queryDTO);
        List<CommodityPageDTO> records = pages.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(pages);
        }

        List<Long> brandIds = records.stream().map(CommodityPageDTO::getBrandId).collect(Collectors.toList());
        List<Long> categoryIds = records.stream().map(CommodityPageDTO::getCategoryId).collect(Collectors.toList());
        Map<Long, String> brandMap = brandGateway.selectByIds(brandIds).stream()
                .collect(Collectors.toMap(Brand::getId, Brand::getName));
        Map<Long, String> categoryMap = categoryGateway.selectByIds(categoryIds).stream()
                .collect(Collectors.toMap(com.ark.center.commodity.domain.category.Category::getId, com.ark.center.commodity.domain.category.Category::getName));

        for (CommodityPageDTO record : records) {
            record.setBrandName(MapUtils.getString(brandMap, record.getBrandId(), ""));
            record.setCategoryName(MapUtils.getString(categoryMap, record.getCategoryId(), ""));
        }
        return PageResponse.of(pages);
    }

    public CommodityDTO getInfo(Long spuId) {
        return commodityDetailsQryExe.execute(spuId);
    }

    public SearchDTO search() {
        return null;
    }
}
