package com.kt.cloud.commodity.module.commodity.service;

import com.kt.cloud.commodity.dao.entity.SpuAttrDO;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.dao.entity.SpuSalesDO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityRespDTO;
import com.kt.component.dto.PageResponse;
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
public class CommodityService {

    private final SpuService spuService;
    private final SkuService skuService;

    public CommodityService(SpuService spuService, SkuService skuService) {
        this.spuService = spuService;
        this.skuService = skuService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long create(CommodityUpdateReqDTO reqDTO) {
        if (reqDTO.getId() != null) {
            return updateCommodity(reqDTO);
        }
        // 添加SPU
        Long spuId = spuService.saveSpu(reqDTO);
        // 添加SKU信息

        skuService.saveSku(spuId, reqDTO);
        return spuId;
    }

    public Long updateCommodity(CommodityUpdateReqDTO reqDTO) {
        return null;
    }

    public PageResponse<CommodityPageRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        return PageResponse.build(spuService.getPageList(queryDTO));
    }

    public CommodityRespDTO getInfo(Long spuId) {
        CommodityRespDTO commodityRespDTO = new CommodityRespDTO();
        SpuDO spuDO = spuService.getById(spuId);
        if (spuDO == null) {
            return null;
        }
        assembleBaseInfo(commodityRespDTO, spuDO);
        assembleSpecList(commodityRespDTO, spuDO);
        assembleParamList(commodityRespDTO, spuDO);
        return commodityRespDTO;
    }

    private void assembleParamList(CommodityRespDTO commodityRespDTO, SpuDO spuDO) {
        List<SpuAttrDO> spuAttrDOList = spuService.getAttrList(spuDO.getId());
        commodityRespDTO.setParamList();
    }

    private void assembleSpecList(CommodityRespDTO commodityRespDTO, SpuDO spuDO) {

    }

    private void assembleBaseInfo(CommodityRespDTO commodityRespDTO, SpuDO spuDO) {
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
        return commodityRespDTO;

    }
}
