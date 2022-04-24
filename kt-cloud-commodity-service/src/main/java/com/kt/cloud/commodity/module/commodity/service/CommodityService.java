package com.kt.cloud.commodity.module.commodity.service;

import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CommodityPageRespDTO getInfo(Long id) {
        return null;
    }
}
