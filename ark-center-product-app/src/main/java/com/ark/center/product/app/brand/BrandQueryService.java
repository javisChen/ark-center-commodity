package com.ark.center.product.app.brand;

import com.ark.center.product.client.brand.dto.BrandDTO;
import com.ark.center.product.client.brand.query.BrandPageQry;
import com.ark.center.product.infra.brand.Brand;
import com.ark.center.product.infra.brand.convertor.BrandConvertor;
import com.ark.center.product.infra.brand.gateway.impl.BrandService;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandQueryService {
    
    private final BrandService brandService;
    private final BrandConvertor brandConvertor;
    
    public BrandDTO queryDetails(Long id) {
        Brand brand = brandService.selectById(id);
        return brandConvertor.toDTO(brand);
    }
    
    public PageResponse<BrandDTO> queryPages(BrandPageQry query) {
        IPage<BrandDTO> pageList = brandService.selectPages(query);
        return PageResponse.of(pageList);
    }
} 