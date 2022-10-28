package com.ark.center.commodity.domain.commodity.assembler;

import cn.hutool.core.collection.CollUtil;
import com.ark.center.commodity.client.commodity.command.AttrCmd;
import com.ark.center.commodity.client.commodity.command.AttrOptionCmd;
import com.ark.center.commodity.client.commodity.command.CommoditySaveCmd;
import com.ark.center.commodity.client.commodity.command.SkuUpdateCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.dto.SkuAttrDTO;
import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.vo.*;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.ark.ddd.base.BaseAssembler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommodityAssembler extends BaseAssembler<Commodity, CommodityDTO> {

    public Sku toSku(SkuUpdateCmd cmd) {
        return new Sku(cmd.getId(),
                cmd.getCode(),
                cmd.getSalesPrice(),
                cmd.getCostPrice(),
                cmd.getStock(),
                cmd.getWarnStock(),
                BeanConvertor.copyList(cmd.getSpecList(), Attr.class));
    }

    public List<Attr> toAttr(List<AttrCmd> cmd) {
        return cmd.stream()
                .map(this::convertFromAttrCmd)
                .collect(Collectors.toList());
    }

    private Attr convertFromAttrCmd(AttrCmd attrCmd) {
        return BeanConvertor.copy(attrCmd, Attr.class);
    }

    public Commodity cmdToCommodity(CommoditySaveCmd cmd) {
        return BeanConvertor.copy(cmd, Commodity.class);
    }

    public SalesInfo toSalesInfo(CommoditySaveCmd cmd) {
        return new SalesInfo(cmd.getId(),
                cmd.getFreightTemplateId(),
                cmd.getPcDetailHtml(),
                cmd.getMobileDetailHtml(),
                toAttr(cmd.getParamList()));
    }

    public List<Picture> toPicture(CommoditySaveCmd cmd) {
        List<String> picList = cmd.getPicList();
        if (CollUtil.isEmpty(picList)) {
            return Collections.emptyList();
        }
        return picList.stream().map(pic -> new Picture(pic, "")).collect(Collectors.toUnmodifiableList());
    }

    public List<AttrOption> toAttrOption(CommoditySaveCmd cmd) {
        List<AttrOptionCmd> newAttrOptionList = cmd.getNewAttrOptionList();
        if (CollUtil.isEmpty(newAttrOptionList)) {
            return Collections.emptyList();
        }
        return newAttrOptionList.stream()
                .map(item -> new AttrOption(item.getAttrId(), item.getValueList()))
                .collect(Collectors.toList());
    }

    public PageResponse<CommodityPageDTO> toPageResponse(IPage<Commodity> pageList) {
        IPage<CommodityPageDTO> page = pageList.convert(item -> {
            CommodityPageDTO dto = BeanConvertor.copy(item, CommodityPageDTO.class);
            dto.setShelfStatus(item.getShelfStatus().getValue());
            return dto;
        });
        return BeanConvertor.copyPage(page, CommodityPageDTO.class);
    }

    public PageResponse<CommodityPageDTO> toPageResponse(IPage<Commodity> pageList,
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

    @Override
    public CommodityDTO toDTO(Commodity category) {
        CommodityDTO commodityDTO = super.toDTO(category);
        List<Picture> picList = category.getPicList();
        commodityDTO.setShelfStatus(category.getShelfStatus().getValue());
        commodityDTO.setPicList(picList.stream().map(Picture::getUrl).collect(Collectors.toList()));
        List<Sku> skuList = category.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {
            commodityDTO.setSkuList(skuList.stream().map(sku -> {
                SkuDTO skuDTO = new SkuDTO();
                skuDTO.setId(sku.getId());
                skuDTO.setCode(sku.getCode());
//            skuDTO.setSpuName(ca);
                skuDTO.setSalesPrice(sku.getSalesPrice());
                skuDTO.setCostPrice(sku.getCostPrice());
                skuDTO.setStock(sku.getStock());
                skuDTO.setWarnStock(sku.getWarnStock());
                skuDTO.setMainPicture(sku.getCode());
                skuDTO.setSpecList(BeanConvertor.copyList(sku.getSpecList(), SkuAttrDTO.class));
                return skuDTO;
            }).collect(Collectors.toList()));
        }
        return commodityDTO;
    }
}














