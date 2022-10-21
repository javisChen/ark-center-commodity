package com.ark.center.commodity.domain.brand.assembler;


import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandAssembler {

    public PageResponse<BrandDTO> entityToDTO(PageResponse<Brand> page) {
        return page.convert(item -> BeanConvertor.copy(item, BrandDTO.class));
    }

    public PageResponse<BrandDTO> entityToDTO(IPage<Brand> page) {
        return BeanConvertor.copyPage(page, BrandDTO.class);
    }

    public List<BrandDTO> entityToDTO(List<Brand> list) {
        return BeanConvertor.copyList(list, BrandDTO.class);
    }

    public PageResponse<BrandDTO> cmdToEntity(PageResponse<Brand> page) {
        return BeanConvertor.copyPage(page, BrandDTO.class);
    }

    public Brand updateCmdToAggregate(BrandUpdateCmd command) {
        return BeanConvertor.copy(command, Brand.class);
    }

    public Brand createCmdToAggregate(BrandCreateCmd command) {
        return BeanConvertor.copy(command, Brand.class);
    }

    public BrandDTO entityToDTO(Brand category) {
        return BeanConvertor.copy(category, BrandDTO.class);
    }
}














