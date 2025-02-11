package com.ark.center.product.app.brand;

import com.ark.center.product.client.brand.command.BrandCmd;
import com.ark.center.product.infra.brand.Brand;
import com.ark.center.product.infra.brand.convertor.BrandConvertor;
import com.ark.center.product.infra.brand.gateway.impl.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandCommandHandler {
    
    private final BrandService brandService;
    private final BrandConvertor brandConvertor;
    
    public Long handle(BrandCmd cmd) {
        Brand brand = brandConvertor.toBrand(cmd);
        if (brand.getId() != null) {
            brandService.update(brand);
            return brand.getId();
        }
        brandService.save(brand);
        return brand.getId();
    }
} 