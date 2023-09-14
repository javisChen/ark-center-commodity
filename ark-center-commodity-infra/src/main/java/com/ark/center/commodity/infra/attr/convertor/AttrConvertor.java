package com.ark.center.commodity.infra.attr.convertor;

import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.dto.AttrOptionDTO;
import com.ark.center.commodity.domain.attr.Attr;
import com.ark.center.commodity.domain.attr.AttrOption;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttrConvertor {

    AttrDTO toDTO(Attr attr);

    List<AttrOptionDTO> toOptionDTO(List<AttrOption> attrOptions);

    AttrOptionDTO toOptionDTO(AttrOption attrOption);
}
