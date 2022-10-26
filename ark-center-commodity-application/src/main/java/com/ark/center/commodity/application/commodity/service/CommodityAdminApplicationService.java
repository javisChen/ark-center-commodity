package com.ark.center.commodity.application.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.ark.center.commodity.api.sku.response.SkuAttrRespDTO;
import com.ark.center.commodity.api.sku.response.SkuRespDTO;
import com.ark.center.commodity.client.commodity.command.CommoditySaveCmd;
import com.ark.center.commodity.client.commodity.dto.CommodityDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.dto.SearchDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.factory.CommodityFactory;
import com.ark.center.commodity.domain.commodity.repository.CommodityRepository;
import com.ark.center.commodity.infrastructure.category.repository.db.CategoryDO;
import com.ark.center.commodity.infrastructure.commodity.convertor.CommodityConvertor;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SkuDO;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SpuDO;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SpuSalesDO;
import com.ark.component.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CommodityAdminApplicationService {

    private final CommodityRepository commodityRepository;

    private final CommodityFactory commodityFactory;

    @Transactional(rollbackFor = Exception.class)
    public Long save(CommoditySaveCmd reqDTO) {
        Commodity commodity = commodityFactory.create(reqDTO);
        return commodityRepository.store(commodity);
    }

    public PageResponse<CommodityPageDTO> getPageList(CommodityPageQry queryDTO) {
        return PageResponse.of(commodityRepository.getPageList(queryDTO));
    }

    public CommodityDTO getInfo(Long spuId) {
        CommodityDTO commodityRespDTO = new CommodityDTO();
        SpuDO spuDO = commodityRepository.getById(spuId);
        if (spuDO == null) {
            return null;
        }
        assembleSpuInfo(commodityRespDTO, spuDO);
        assembleSkuList(commodityRespDTO, spuDO);
        return commodityRespDTO;
    }

    private void assembleSkuList(CommodityDTO commodityRespDTO, SpuDO spuDO) {
        List<SkuDO> skuList = commodityRepository.listBySpuId(spuDO.getId());
        List<SkuRespDTO> skuDTOList = CommodityConvertor.convertToSkuDTO(skuList);
        commodityRespDTO.setSkuList(skuDTOList);

    }

    private void assembleSpuInfo(CommodityDTO commodityRespDTO, SpuDO spuDO) {
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

        CategoryDO categoryDO = categoryAdminService.getById(spuDO.getCategoryId());
        commodityRespDTO.setCategoryLevelPath(categoryDO.getLevelPath());
    }

    public SearchDTO search() {
        return null;
    }
}
