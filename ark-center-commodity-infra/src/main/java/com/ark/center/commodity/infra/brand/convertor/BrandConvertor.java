package com.ark.center.commodity.infra.brand.convertor;

import com.ark.center.commodity.client.brand.command.BrandCreateCmd;
import com.ark.center.commodity.client.brand.command.BrandUpdateCmd;
import com.ark.center.commodity.client.brand.dto.BrandDTO;
import com.ark.center.commodity.domain.brand.Brand;
import com.ark.component.dto.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandConvertor {

    PageResponse<BrandDTO> cmdToEntity(PageResponse<Brand> page);

    Brand updateCmdToAggregate(BrandUpdateCmd command) ;

    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Brand toBrand(BrandCreateCmd command);

    BrandDTO toDTO(Brand brand);

}
