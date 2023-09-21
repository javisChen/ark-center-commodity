package com.ark.center.commodity.infra.attr.convertor;

import com.ark.center.commodity.client.attr.command.AttrGroupCmd;
import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.domain.attr.AttrGroup;
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
