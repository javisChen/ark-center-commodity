package com.ark.center.commodity.infra.brand.convertor;

import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.domain.brand.Brand;
import com.ark.component.dto.PageResponse;
import org.mapstruct.Mapper;

MappingConstants.ComponentModel.SPRING
public interface BrandConvertor {

    public PageResponse<BrandDTO> cmdToEntity(PageResponse<Brand> page);

    public Brand updateCmdToAggregate(BrandUpdateCmd command) ;

    public Brand createCmdToAggregate(BrandCreateCmd command);

    public BrandDTO toDTO(Brand brand);

}
