package com.kt.cloud.commodity.module.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.SkuAttrDO;
import com.kt.cloud.commodity.dao.entity.SkuDO;
import com.kt.cloud.commodity.dao.mapper.SkuMapper;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.AttrReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.SkuUpdateReqDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * sku 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class SkuService extends ServiceImpl<SkuMapper, SkuDO> implements IService<SkuDO> {

    private final SkuAttrService skuAttrService;

    public SkuService(SkuAttrService skuAttrService) {
        this.skuAttrService = skuAttrService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveSku(Long spuId, CommodityUpdateReqDTO reqDTO) {
        List<SkuUpdateReqDTO> skuList = reqDTO.getSkuList();
        for (SkuUpdateReqDTO skuUpdateReqDTO : skuList) {
            SkuDO skuDO = assembleSkuDTO(spuId, skuUpdateReqDTO);
            save(skuDO);
            saveSkuAttrs(skuDO.getId(), skuUpdateReqDTO.getSpecList());
        }
    }

    private void saveSkuAttrs(Long skuId, List<AttrReqDTO> skuAttrList) {
        List<SkuAttrDO> doList = skuAttrList.stream().map(item -> {
            SkuAttrDO skuAttrDO = new SkuAttrDO();
            skuAttrDO.setSkuId(skuId);
            skuAttrDO.setAttrValue(item.getAttrValue());
            return skuAttrDO;
        }).collect(Collectors.toList());
        skuAttrService.saveBatch(doList);
    }

    private SkuDO assembleSkuDTO(Long spuId, SkuUpdateReqDTO entity) {
        SkuDO skuDO = new SkuDO();
        skuDO.setSpuId(spuId);
        skuDO.setCode(entity.getCode());
        skuDO.setSalesPrice(entity.getSalesPrice());
        skuDO.setCostPrice(entity.getCostPrice());
        skuDO.setStock(entity.getStock());
        skuDO.setWarnStock(entity.getWarnStock());
        skuDO.setParamData(JSONObject.toJSONString(entity.getSpecList()));
        return skuDO;
    }
}
