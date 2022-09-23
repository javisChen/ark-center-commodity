package com.kt.cloud.commodity.module.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.kt.cloud.commodity.api.sku.response.SkuAttrRespDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.cloud.commodity.dao.entity.*;
import com.kt.cloud.commodity.module.attr.dto.request.AttrPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.cloud.commodity.module.attr.service.AttrService;
import com.kt.cloud.commodity.module.category.service.CategoryAdminService;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.AppCommodityRespDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.SearchRespDTO;
import com.kt.cloud.commodity.module.commodity.support.CommodityConvertor;
import com.kt.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 商品服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
@RequiredArgsConstructor
public class CommodityService {

    private final SpuService spuService;
    private final SkuService skuService;

    private final AttrService attrService;

    public PageResponse<CommodityPageRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        return PageResponse.of(spuService.getPageList(queryDTO));
    }

    public AppCommodityRespDTO getInfo(Long spuId) {
        AppCommodityRespDTO commodityRespDTO = new AppCommodityRespDTO();
        SpuDO spuDO = spuService.getById(spuId);
        if (spuDO == null) {
            return null;
        }
        assembleAttrList(spuId, commodityRespDTO, spuDO);
        assembleSpuInfo(commodityRespDTO, spuDO);
        assembleSkuList(commodityRespDTO, spuDO);
        return commodityRespDTO;
    }

    private void assembleAttrList(Long spuId, AppCommodityRespDTO commodityRespDTO, SpuDO spuDO) {
        AttrPageQueryReqDTO queryDTO = new AttrPageQueryReqDTO();
        queryDTO.setCategoryId(spuDO.getCategoryId());
        queryDTO.setWithOptions(true);
        queryDTO.setSpuId(spuId);
        queryDTO.setType(AttrDO.Type.SPEC.getValue());
        queryDTO.setCurrent(1);
        queryDTO.setSize(30);
        commodityRespDTO.setAttrList(attrService.getPageList(queryDTO).getRecords());
    }

    private void assembleSkuList(AppCommodityRespDTO commodityRespDTO, SpuDO spuDO) {
        List<SkuDO> skuList = skuService.listBySpuId(spuDO.getId());
        List<SkuRespDTO> skuDTOList = CommodityConvertor.convertToSkuDTO(skuList);
        commodityRespDTO.setSkuList(skuDTOList);
    }

    private void assembleSpuInfo(AppCommodityRespDTO commodityRespDTO, SpuDO spuDO) {
        Long spuId = spuDO.getId();
        commodityRespDTO.setId(spuId);
        commodityRespDTO.setName(spuDO.getName());
        commodityRespDTO.setDescription(spuDO.getDescription());
        commodityRespDTO.setMainPicture(spuDO.getMainPicture());
        commodityRespDTO.setPrice(spuDO.getShowPrice());

        List<String> picList = spuService.getPicList(spuId);
        commodityRespDTO.setPicList(picList);

        SpuSalesDO spuSalesDO = spuService.getSalesInfo(spuId);
        commodityRespDTO.setMobileDetailHtml(spuSalesDO.getMobileDetailHtml());
        commodityRespDTO.setPcDetailHtml(spuSalesDO.getPcDetailHtml());
        commodityRespDTO.setParamList(JSONObject.parseArray(spuSalesDO.getParamData(), SkuAttrRespDTO.class));

    }

}
