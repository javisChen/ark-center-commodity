package com.ark.center.commodity.domain.spu.assembler;

import cn.hutool.core.collection.CollUtil;
import com.ark.center.commodity.client.commodity.command.AttrCmd;
import com.ark.center.commodity.client.commodity.command.AttrOptionCmd;
import com.ark.center.commodity.client.commodity.command.CommodityCreateCmd;
import com.ark.center.commodity.client.commodity.command.SkuCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.aggregate.Commodity;
import com.ark.center.commodity.domain.spu.vo.*;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections4.MapUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

MappingConstants.ComponentModel.SPRING
public interface SpuAssembler {

    public default Sku toSku(SkuCmd cmd) {
        return new Sku(cmd.getId(),
                cmd.getCode(),
                "",
                cmd.getSalesPrice(),
                cmd.getCostPrice(),
                cmd.getStock(),
                cmd.getWarnStock(),
                "",
                BeanConvertor.copyList(cmd.getSpecList(), Attr.class));
    }

    public default List<Attr> toAttr(List<AttrCmd> cmd) {
        return cmd.stream()
                .map(this::convertFromAttrCmd)
                .collect(Collectors.toList());
    }

    private Attr convertFromAttrCmd(AttrCmd attrCmd) {
        return BeanConvertor.copy(attrCmd, Attr.class);
    }

    public default Commodity cmdToCommodity(CommodityCreateCmd cmd) {
        return BeanConvertor.copy(cmd, Commodity.class);
    }

    public default SalesInfo toSalesInfo(CommodityCreateCmd cmd) {
        return new SalesInfo(cmd.getId(),
                cmd.getFreightTemplateId(),
                cmd.getPcDetailHtml(),
                cmd.getMobileDetailHtml(),
                toAttr(cmd.getParamList()));
    }

    public default List<Picture> toPicture(CommodityCreateCmd cmd) {
        List<String> picList = cmd.getPicList();
        if (CollUtil.isEmpty(picList)) {
            return Collections.emptyList();
        }
        return picList.stream().map(pic -> new Picture(pic, "")).toList();
    }

    public default List<AttrOption> toAttrOption(CommodityCreateCmd cmd) {
        List<AttrOptionCmd> newAttrOptionList = cmd.getNewAttrOptionList();
        if (CollUtil.isEmpty(newAttrOptionList)) {
            return Collections.emptyList();
        }
        return newAttrOptionList.stream()
                .map(item -> new AttrOption(item.getAttrId(), item.getValueList()))
                .collect(Collectors.toList());
    }

    public default PageResponse<CommodityPageDTO> toPageResponse(IPage<Commodity> pageList) {
        IPage<CommodityPageDTO> page = pageList.convert(item -> {
            return BeanConvertor.copy(item, CommodityPageDTO.class);
        });
        return BeanConvertor.copyPage(page, CommodityPageDTO.class);
    }

    public default PageResponse<CommodityPageDTO> toPageResponse(IPage<Commodity> pageList,
                                                         Map<Long, String> brandMap,
                                                         Map<Long, String> categoryMap) {
        IPage<CommodityPageDTO> page = pageList.convert(item -> {
            CommodityPageDTO dto = BeanConvertor.copy(item, CommodityPageDTO.class);
            dto.setBrandName(MapUtils.getString(brandMap, item.getBrandId(), ""));
            dto.setCategoryName(MapUtils.getString(categoryMap, item.getCategoryId(), ""));
            dto.setShelfStatus(item.getShelfStatus().getValue());
            return dto;
        });
        return BeanConvertor.copyPage(page, CommodityPageDTO.class);
    }

//    @Override
//    public default CommodityDTO toDTO(Commodity category) {
//        CommodityDTO commodityDTO = super.toDTO(category);
//        List<Picture> picList = category.getPicList();
//        commodityDTO.setShelfStatus(category.getShelfStatus().getValue());
//        commodityDTO.setPicList(picList.stream().map(Picture::getUrl).collect(Collectors.toList()));
//        List<Sku> skuList = category.getSkuList();
//        if (CollectionUtils.isNotEmpty(skuList)) {
//            commodityDTO.setSkuList(skuList.stream().map(sku -> {
//                SkuDTO skuDTO = new SkuDTO();
//                skuDTO.setId(sku.getId());
//                skuDTO.setCode(sku.getCode());
////            skuDTO.setSpuName(ca);
//                skuDTO.setSalesPrice(sku.getSalesPrice());
//                skuDTO.setCostPrice(sku.getCostPrice());
//                skuDTO.setStock(sku.getStock());
//                skuDTO.setWarnStock(sku.getWarnStock());
//                skuDTO.setMainPicture(sku.getCode());
//                skuDTO.setSpecList(BeanConvertor.copyList(sku.getSpecList(), SkuAttrDTO.class));
//                return skuDTO;
//            }).collect(Collectors.toList()));
//        }
//        return commodityDTO;
//    }

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
}














