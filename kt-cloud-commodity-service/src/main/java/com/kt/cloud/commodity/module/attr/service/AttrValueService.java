package com.kt.cloud.commodity.module.attr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.AttrOptionDO;
import com.kt.cloud.commodity.dao.mapper.AttrValueMapper;
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
public class AttrValueService extends ServiceImpl<AttrValueMapper, AttrOptionDO> implements IService<AttrOptionDO> {

    public void batchSave(Long attrId, List<String> values, Integer type) {
        List<AttrOptionDO> dos = new ArrayList<>(values.size());
        for (String value : values) {
            AttrOptionDO valueDO = new AttrOptionDO();
            valueDO.setAttrId(attrId);
            valueDO.setValue(value);
            valueDO.setType(type);
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

    public List<AttrOptionDO> listByAttrIds(List<Long> attrIds) {
        return lambdaQuery()
                .in(AttrOptionDO::getAttrId, attrIds)
                .list();
    }
}
