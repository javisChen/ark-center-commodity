//package com.ark.center.commodity.module.commodity.support;
//
//import com.alibaba.fastjson.JSON;
//import com.ark.center.commodity.api.sku.response.SkuAttrRespDTO;
//import com.ark.center.commodity.infra.commodity.repository.db.SkuDO;
//import com.google.common.collect.Lists;
//import com.ark.center.commodity.client.commodity.dto.SkuRespDTO;
//
//import java.util.List;
//
//public class CommodityConvertor {
//
//    public static List<SkuRespDTO> convertToSkuDTO(List<SkuDO> skuList) {
//        List<SkuRespDTO> skuDTOList = Lists.newArrayListWithCapacity(skuList.size());
//        for (SkuDO skuDO : skuList) {
//            SkuRespDTO skuRespDTO = new SkuRespDTO();
//            skuRespDTO.setId(skuDO.getId());
//            skuRespDTO.setSpuName(skuDO.getSpuName());
//            skuRespDTO.setCode(skuDO.getCode());
//            skuRespDTO.setSalesPrice(skuDO.getSalesPrice());
//            skuRespDTO.setCostPrice(skuDO.getCostPrice());
//            skuRespDTO.setStock(skuDO.getStock());
//            skuRespDTO.setWarnStock(skuDO.getWarnStock());
//            skuRespDTO.setSpecList(JSON.parseArray(skuDO.getSpecData(), SkuAttrRespDTO.class));
//            skuRespDTO.setMainPicture(skuDO.getMainPicture());
//            skuDTOList.add(skuRespDTO);
//        }
//        return skuDTOList;
//    }
//
//}
