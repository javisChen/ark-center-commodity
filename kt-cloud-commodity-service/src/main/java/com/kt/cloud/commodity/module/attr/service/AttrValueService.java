package com.kt.cloud.commodity.module.attr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.AttrValueDO;
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
public class AttrValueService extends ServiceImpl<AttrValueMapper, AttrValueDO> implements IService<AttrValueDO> {

    public void batchSave(Long attrId, List<String> values, Integer type) {
        List<AttrValueDO> dos = new ArrayList<>(values.size());
        for (String value : values) {
            AttrValueDO valueDO = new AttrValueDO();
            valueDO.setAttrId(attrId);
            valueDO.setContent(value);
            valueDO.setType(type);
            dos.add(valueDO);
        }
        saveBatch(dos);
    }

    public void removeByAttrId(Long attrId) {
        lambdaUpdate()
                .eq(AttrValueDO::getAttrId, attrId)
                .remove();
    }

    public List<AttrValueDO> listByAttrId(Long attrId) {
        return lambdaQuery()
                .eq(AttrValueDO::getAttrId, attrId)
                .list();
    }

    public List<AttrValueDO> listByAttrIds(List<Long> attrIds) {
        return lambdaQuery()
                .in(AttrValueDO::getAttrId, attrIds)
                .list();
    }
}
