package com.kt.cloud.commodity.module.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.SkuAttrDO;
import com.kt.cloud.commodity.dao.entity.SkuDO;
import com.kt.cloud.commodity.dao.mapper.SkuMapper;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.SkuAttrUpdateDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.SkuUpdateDTO;
import org.springframework.stereotype.Service;

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

    public void saveSku(Long spuId, CommodityUpdateReqDTO reqDTO) {
        List<SkuUpdateDTO> skuList = reqDTO.getSkuList();
        for (SkuUpdateDTO skuUpdateDTO : skuList) {
            SkuDO skuDO = assembleSkuDTO(spuId, skuUpdateDTO);
            save(skuDO);
            saveSkuAttrs(skuDO.getId(), skuUpdateDTO.getSkuAttrList());
        }
    }

    private void saveSkuAttrs(Long skuId, List<SkuAttrUpdateDTO> skuAttrList) {
        List<SkuAttrDO> doList = skuAttrList.stream().map(item -> {
            SkuAttrDO skuAttrDO = new SkuAttrDO();
            skuAttrDO.setSkuId(skuId);
            skuAttrDO.setAttrValue(item.getAttrValue());
            return skuAttrDO;
        }).collect(Collectors.toList());
        skuAttrService.saveBatch(doList);
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
}
