package com.kt.cloud.commodity.module.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.kt.cloud.commodity.dao.entity.SkuDO;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.SkuUpdateDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.SkuRespDTO;
import com.kt.component.dto.PageResponse;
import org.jetbrains.annotations.NotNull;
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

    private SpuService spuService;
    private SkuService skuService;

    public CommodityService(SpuService spuService, SkuService skuService) {
        this.spuService = spuService;
        this.skuService = skuService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long create(CommodityUpdateReqDTO reqDTO) {
        // 添加商品基本信息
        SpuDO spuDO = assembleSpuDO(reqDTO);
        spuService.save(spuDO);

        // 添加SKU信息
        List<SkuUpdateDTO> skuList = reqDTO.getSkuList();
        for (SkuUpdateDTO skuUpdateDTO : skuList) {
            skuService.save(assembleSkuDTO(spuDO.getId(), skuUpdateDTO));
        }
        return 0L;
    }

    private SkuDO assembleSkuDTO(Long spuId, SkuUpdateDTO entity) {
        SkuDO skuDO = new SkuDO();
        skuDO.setSpuId(spuId);
        skuDO.setCode(entity.getCode());
        skuDO.setSalesPrice(entity.getSalesPrice());
        skuDO.setCostPrice(entity.getCostPrice());
        skuDO.setStock(entity.getStock());
        skuDO.setWarnStock(entity.getWarnStock());
        skuDO.setParamData(JSONObject.toJSONString(entity.getSkuAttrList()));
        return skuDO;
    }

    @NotNull
    private SpuDO assembleSpuDO(CommodityUpdateReqDTO reqDTO) {
        SpuDO spuDO = new SpuDO();
        spuDO.setName(reqDTO.getName());
        spuDO.setCode(reqDTO.getCode());
        spuDO.setDescription(reqDTO.getDescription());
        spuDO.setMainPicture(reqDTO.getMainPicture());
        spuDO.setShelfStatus(reqDTO.getShelfStatus());
        spuDO.setVerifyStatus(reqDTO.getVerifyStatus());
        spuDO.setShowPrice(reqDTO.getShowPrice());
        spuDO.setUnit(reqDTO.getUnit());
        spuDO.setWeight(reqDTO.getWeight());
        spuDO.setBrandId(reqDTO.getBrandId());
        spuDO.setCategoryId(reqDTO.getCategoryId());
        return spuDO;
    }

    public Long updateCommodity(CommodityUpdateReqDTO reqDTO) {
        return null;
    }

    public PageResponse<SkuRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        return null;
    }

    public SkuRespDTO getInfo(Long id) {
        return null;
    }
}
