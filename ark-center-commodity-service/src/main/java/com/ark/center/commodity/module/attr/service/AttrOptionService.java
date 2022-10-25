package com.ark.center.commodity.module.attr.service;

import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionDO;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性值 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Service
public class AttrOptionService extends ServiceImpl<AttrOptionMapper, AttrOptionDO> implements IService<AttrOptionDO> {

    public void batchSave(Long attrId, List<String> values, Integer type) {
        batchSave(attrId, values, type, null);
    }

    public void batchSave(Long attrId, List<String> values, Integer type, Long spuId) {
        List<AttrOptionDO> dos = new ArrayList<>(values.size());
        for (String value : values) {
            AttrOptionDO valueDO = new AttrOptionDO();
            valueDO.setAttrId(attrId);
            valueDO.setValue(value);
            valueDO.setType(type);
            if (spuId != null && spuId > 0) {
                valueDO.setSpuId(spuId);
            }
            dos.add(valueDO);
        }
        saveBatch(dos);
    }

    public void removeByAttrId(Long attrId) {
        lambdaUpdate()
                .eq(AttrOptionDO::getAttrId, attrId)
                .remove();
    }

    public List<AttrOptionDO> listByAttrId(Long attrId) {
        return lambdaQuery()
                .eq(AttrOptionDO::getAttrId, attrId)
                .list();
    }

    public List<AttrOptionDO> listByAttrIdsAndType(List<Long> attrIds, Integer type) {
        return lambdaQuery()
                .in(AttrOptionDO::getAttrId, attrIds)
                .eq(AttrOptionDO::getType, type)
                .list();
    }

    public List<AttrOptionDO> listByAttrIds(List<Long> attrIds) {
        return lambdaQuery()
                .in(AttrOptionDO::getAttrId, attrIds)
                .list();
    }

    public List<AttrOptionDO> listBySpuId(Long spuId) {
        return lambdaQuery()
                .eq(AttrOptionDO::getSpuId, spuId)
                .eq(AttrOptionDO::getType, AttrOptionDO.Type.EXCLUSIVE.getValue())
                .list();
    }
}
