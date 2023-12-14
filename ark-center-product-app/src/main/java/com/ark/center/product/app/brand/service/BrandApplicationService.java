package com.ark.center.product.app.brand.service;

import com.ark.center.product.client.brand.command.BrandCmd;
import com.ark.center.product.client.brand.dto.BrandDTO;
import com.ark.center.product.client.brand.query.BrandPageQry;
import com.ark.center.product.domain.brand.Brand;
import com.ark.center.product.domain.brand.gateway.BrandGateway;
import com.ark.center.product.infra.brand.convertor.BrandConvertor;
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

    public Long save(BrandCmd createCmd) {
        Brand brand = brandConvertor.toBrand(createCmd);
        if (brand.getId() != null) {
            brandGateway.update(brand);
            return brand.getId();
        }
        return brandGateway.insert(brand);
    }

    public PageResponse<BrandDTO> queryPages(BrandPageQry pageQry) {
        IPage<BrandDTO> pageList = brandGateway.selectPages(pageQry);
        return PageResponse.of(pageList);
    }

    public BrandDTO queryDetails(Long brandId) {
        Brand brand = brandGateway.selectById(brandId);
        return brandConvertor.toDTO(brand);
    }

}
