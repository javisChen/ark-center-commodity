package com.ark.center.commodity.app.brand.service;

import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.client.brand.query.BrandPageQry;
import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.center.commodity.domain.brand.assembler.BrandAssembler;
import com.ark.center.commodity.domain.brand.repository.BrandRepository;
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

    private final BrandRepository brandRepository;

    private final BrandAssembler brandAssembler;

    public Long createBrand(BrandCreateCmd reqDTO) {
        Brand aggregate = brandAssembler.createCmdToAggregate(reqDTO);
        return brandRepository.store(aggregate);
    }

    public PageResponse<BrandDTO> getPageList(BrandPageQry pageQry) {
        IPage<Brand> pageList = brandRepository.pageList(pageQry);
        return brandAssembler.toDTO(pageList);
    }

    public void updateBrand(BrandUpdateCmd reqDTO) {
        Brand aggregate = brandAssembler.updateCmdToAggregate(reqDTO);
        brandRepository.update(aggregate);
    }

    public BrandDTO getBrandInfo(Long brandId) {
        Brand brand = brandRepository.findById(brandId);
        return brandAssembler.toDTO(brand);
    }
}
