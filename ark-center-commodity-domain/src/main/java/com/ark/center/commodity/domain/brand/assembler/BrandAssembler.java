package com.ark.center.commodity.domain.brand.assembler;


import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.domain.brand.aggregate.Brand;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class BrandAssembler extends BaseAssembler<Brand, BrandDTO> {

    public PageResponse<BrandDTO> cmdToEntity(PageResponse<Brand> page) {
        return BeanConvertor.copyPage(page, BrandDTO.class);
    }

    public Brand updateCmdToAggregate(BrandUpdateCmd command) {
        return BeanConvertor.copy(command, Brand.class);
    }

    public Brand createCmdToAggregate(BrandCreateCmd command) {
        return BeanConvertor.copy(command, Brand.class);
    }

}














