package com.ark.center.product.infra.attr.convertor;

import com.ark.center.product.client.attr.command.AttrGroupCmd;
import com.ark.center.product.client.attr.dto.AttrGroupDTO;
import com.ark.center.product.domain.attr.AttrGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttrGroupConverter {


    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    AttrGroup toAttrGroup(AttrGroupCmd cmd);

    @Mapping(target = "attrList", ignore = true)
    AttrGroupDTO toDTO(AttrGroup attrGroup);
}
