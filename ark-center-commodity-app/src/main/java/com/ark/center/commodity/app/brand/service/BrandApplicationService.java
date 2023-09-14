package com.ark.center.commodity.app.brand.service;

import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.Brand;
import com.ark.center.commodity.domain.brand.gateway.BrandGateway;
import com.ark.center.commodity.infra.brand.convertor.BrandConvertor;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-02-25
 */
@Service
@RequiredArgsConstructor
public class BrandApplicationService {

    private final BrandGateway brandGateway;

    private final BrandConvertor brandConvertor;

    public Long createBrand(BrandCreateCmd reqDTO) {
        Brand aggregate = brandConvertor.createCmdToAggregate(reqDTO);
        return brandGateway.insert(aggregate);
    }

    public PageResponse<BrandDTO> getPageList(BrandPageQry pageQry) {
        IPage<BrandDTO> pageList = brandGateway.selectPages(pageQry);
        return PageResponse.of(pageList);
    }

    public void updateBrand(BrandUpdateCmd reqDTO) {
        Brand aggregate = brandConvertor.updateCmdToAggregate(reqDTO);
        brandGateway.update(aggregate);
    }

    public BrandDTO getBrandInfo(Long brandId) {
        Brand brand = brandGateway.selectById(brandId);
        return brandConvertor.toDTO(brand);
    }
}
