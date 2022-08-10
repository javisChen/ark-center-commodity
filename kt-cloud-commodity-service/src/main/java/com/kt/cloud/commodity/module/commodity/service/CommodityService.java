package com.kt.cloud.commodity.module.commodity.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kt.cloud.commodity.dao.entity.CategoryDO;
import com.kt.cloud.commodity.dao.entity.SkuDO;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.dao.entity.SpuSalesDO;
import com.kt.cloud.commodity.module.category.service.CategoryService;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.api.sku.response.SkuAttrRespDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityRespDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.cloud.commodity.module.commodity.support.CommodityConvertor;
import com.kt.component.dto.PageResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class CommodityService {

    private final SpuService spuService;
    private final SkuService skuService;
    private final CategoryService categoryService;

    public CommodityService(SpuService spuService,
                            SkuService skuService,
                            CategoryService categoryService) {
        this.spuService = spuService;
        this.skuService = skuService;
        this.categoryService = categoryService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long save(CommodityUpdateReqDTO reqDTO) {
        // 保存spu信息
        Long spuId = spuService.saveSpu(reqDTO);
        // 保存SKU信息
        skuService.saveSku(spuId, reqDTO);
        return spuId;
    }

    public PageResponse<CommodityPageRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        return PageResponse.of(spuService.getPageList(queryDTO));
    }

    public CommodityRespDTO getInfo(Long spuId) {
        CommodityRespDTO commodityRespDTO = new CommodityRespDTO();
        SpuDO spuDO = spuService.getById(spuId);
        if (spuDO == null) {
            return null;
        }
        assembleSpuInfo(commodityRespDTO, spuDO);
        assembleSkuList(commodityRespDTO, spuDO);
        return commodityRespDTO;
    }

    private void assembleSkuList(CommodityRespDTO commodityRespDTO, SpuDO spuDO) {
        List<SkuDO> skuList = skuService.listBySpuId(spuDO.getId());
        List<SkuRespDTO> skuDTOList = CommodityConvertor.convertToSkuDTO(skuList);
        commodityRespDTO.setSkuList(skuDTOList);

    }

    private void assembleSpuInfo(CommodityRespDTO commodityRespDTO, SpuDO spuDO) {
        Long spuId = spuDO.getId();
        commodityRespDTO.setId(spuId);
        commodityRespDTO.setName(spuDO.getName());
        commodityRespDTO.setCode(spuDO.getCode());
        commodityRespDTO.setDescription(spuDO.getDescription());
        commodityRespDTO.setBrandId(spuDO.getBrandId());
        commodityRespDTO.setCategoryId(spuDO.getCategoryId());
        commodityRespDTO.setMainPicture(spuDO.getMainPicture());
        commodityRespDTO.setShelfStatus(spuDO.getShelfStatus());
        commodityRespDTO.setShowPrice(spuDO.getShowPrice());
        commodityRespDTO.setUnit(spuDO.getUnit());
        commodityRespDTO.setWeight(spuDO.getWeight());

        List<String> picList = spuService.getPicList(spuId);
        commodityRespDTO.setPicList(picList);

        SpuSalesDO spuSalesDO = spuService.getSalesInfo(spuId);
        commodityRespDTO.setMobileDetailHtml(spuSalesDO.getMobileDetailHtml());
        commodityRespDTO.setPcDetailHtml(spuSalesDO.getPcDetailHtml());
        commodityRespDTO.setParamList(JSONObject.parseArray(spuSalesDO.getParamData(), SkuAttrRespDTO.class));

        CategoryDO categoryDO = categoryService.getById(spuDO.getCategoryId());
        commodityRespDTO.setCategoryLevelPath(categoryDO.getLevelPath());
    }
}
