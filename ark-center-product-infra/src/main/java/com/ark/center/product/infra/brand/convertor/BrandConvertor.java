package com.ark.center.product.infra.brand.convertor;

import com.ark.center.product.client.brand.command.BrandCmd;
import com.ark.center.product.client.brand.dto.BrandDTO;
import com.ark.center.product.domain.brand.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandConvertor {

    @Mapping(target = "sort", ignore = true)
    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Brand toBrand(BrandCmd command);

    BrandDTO toDTO(Brand brand);

}
