package com.ark.center.product.infra.attr.convertor;

import com.ark.center.product.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.product.client.attr.dto.AttrTemplateDTO;
import com.ark.center.product.domain.attr.AttrTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttrTemplateConverter {

    @Mapping(target = "specCount", ignore = true)
    @Mapping(target = "paramCount", ignore = true)
    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    AttrTemplate toAttrTemplate(AttrTemplateCreateCmd cmd);

    AttrTemplateDTO toDTO(AttrTemplate attrTemplate);

    List<AttrTemplateDTO> toDTO(List<AttrTemplate> attrTemplates);
}
