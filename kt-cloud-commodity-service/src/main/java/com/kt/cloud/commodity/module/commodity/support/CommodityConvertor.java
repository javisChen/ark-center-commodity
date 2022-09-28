package com.kt.cloud.commodity.module.commodity.support;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kt.cloud.commodity.api.sku.response.SkuAttrRespDTO;
import com.kt.cloud.commodity.api.sku.response.SkuRespDTO;
import com.kt.cloud.commodity.dao.entity.SkuDO;

import java.util.ArrayList;
import java.util.List;

public class CommodityConvertor {

    public static List<SkuRespDTO> convertToSkuDTO(List<SkuDO> skuList) {
        List<SkuRespDTO> skuDTOList = Lists.newArrayListWithCapacity(skuList.size());
        for (SkuDO skuDO : skuList) {
            SkuRespDTO skuRespDTO = new SkuRespDTO();
            skuRespDTO.setId(skuDO.getId());
            skuRespDTO.setSpuName(skuDO.getSpuName());
            skuRespDTO.setCode(skuDO.getCode());
            skuRespDTO.setSalesPrice(skuDO.getSalesPrice());
            skuRespDTO.setCostPrice(skuDO.getCostPrice());
            skuRespDTO.setStock(skuDO.getStock());
            skuRespDTO.setWarnStock(skuDO.getWarnStock());
            skuRespDTO.setSpecList(JSON.parseArray(skuDO.getSpecData(), SkuAttrRespDTO.class));
            skuRespDTO.setMainPicture(skuDO.getMainPicture());
            skuDTOList.add(skuRespDTO);
        }
        return skuDTOList;
    }

}
