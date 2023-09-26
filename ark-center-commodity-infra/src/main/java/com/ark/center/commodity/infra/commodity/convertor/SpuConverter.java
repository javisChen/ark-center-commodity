package com.ark.center.commodity.infra.commodity.convertor;

import com.ark.center.commodity.client.commodity.command.CommodityCreateCmd;
import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuAttr;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpuConverter {

    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "brandName", ignore = true)
    CommodityPageDTO toCommodityPageDTO(Spu spu);

    @Mapping(target = "skuList", ignore = true)
    @Mapping(target = "picList", ignore = true)
    @Mapping(target = "pcDetailHtml", ignore = true)
    @Mapping(target = "paramList", ignore = true)
    @Mapping(target = "mobileDetailHtml", ignore = true)
    @Mapping(target = "freightTemplateId", ignore = true)
    @Mapping(target = "categoryLevelPath", ignore = true)
    CommodityDTO toCommodityDTO(Spu spu);

    @Mapping(target = "verifyStatus", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "modifier", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "creator", ignore = true)
    Spu toSpu(CommodityCreateCmd cmd);

    List<AttrDTO> toAttrDTO(List<SpuAttr> records);

    AttrDTO toAttrDTO(SpuAttr record);
}
