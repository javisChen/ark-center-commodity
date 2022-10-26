package com.ark.center.commodity.infrastructure.commodity.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.repository.CommodityRepository;
import com.ark.center.commodity.domain.commodity.vo.Attr;
import com.ark.center.commodity.domain.commodity.vo.AttrOption;
import com.ark.center.commodity.domain.commodity.vo.Picture;
import com.ark.center.commodity.domain.commodity.vo.SalesInfo;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionDO;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionMapper;
import com.ark.center.commodity.infrastructure.commodity.AttachmentBizType;
import com.ark.center.commodity.infrastructure.commodity.convertor.CommodityConvertor;
import com.ark.center.commodity.infrastructure.commodity.repository.db.*;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommodityRepositoryImpl implements CommodityRepository {

    private final CommodityConvertor convertor;
    private final SpuMapper spuMapper;
    private final SkuMapper skuMapper;
    private final SpuAttrMapper spuAttrMapper;
    private final AttachmentMapper attachmentMapper;
    private final SpuSalesMapper spuSalesMapper;
    private final AttrOptionMapper attrOptionMapper;

    @Override
    public Long store(Commodity aggregate) {

        saveSpu(aggregate);

        saveSku(aggregate);
        return null;
    }

    private void saveSku(Commodity aggregate) {

    }

    private void saveSpu(Commodity aggregate) {
        SpuDO spuDO = convertor.convertToSpuDO(aggregate);
        // 保存SPU基本信息
        saveBaseInfo(spuDO);
        // 保存销售信息
        saveSalesInfo(spuDO, aggregate);
        // 保存图片
        savePictures(aggregate, spuDO);
        // 保存参数属性
        saveParams(aggregate, spuDO);
        // 保存属性项
        saveAttrOptions(aggregate, spuDO);
    }

    private void saveAttrOptions(Commodity aggregate, SpuDO spuDO) {
        List<AttrOption> attrOptionList = aggregate.getAttrOptionList();
        if (CollectionUtils.isNotEmpty(attrOptionList)) {
            for (AttrOption attrOptionReqDTO : attrOptionList) {
                batchSaveAttrOption(spuDO, attrOptionReqDTO);
            }
        }
    }

    private void batchSaveAttrOption(SpuDO spuDO, AttrOption option) {
        Long attrId = option.getAttrId();
        List<String> values = option.getValueList();
        Long spuId = spuDO.getId();
        List<AttrOptionDO> dos = new ArrayList<>(values.size());
        for (String value : values) {
            AttrOptionDO valueDO = new AttrOptionDO();
            valueDO.setAttrId(attrId);
            valueDO.setValue(value);
            valueDO.setType(com.ark.center.commodity.domain.attr.vo.AttrOption.Type.EXCLUSIVE.getValue());
            if (spuId != null && spuId > 0) {
                valueDO.setSpuId(spuId);
            }
            dos.add(valueDO);
        }
        dos.forEach(attrOptionMapper::insert);
    }

    private void saveParams(Commodity aggregate, SpuDO spuDO) {
        Long spuId = spuDO.getId();
        if (aggregate.getId() != null) {
            removeSpuAttrById(spuId);
        }
        List<Attr> paramList = aggregate.getSalesInfo().getParamData();
        List<SpuAttrDO> attrDOList = paramList.stream().map(item -> {
            SpuAttrDO spuAttrDO = new SpuAttrDO();
            spuAttrDO.setSpuId(spuId);
            spuAttrDO.setAttrValue(item.getAttrValue());
            return spuAttrDO;
        }).collect(Collectors.toList());

        attrDOList.forEach(spuAttrMapper::insert);
    }

    private void removeSpuAttrById(Long spuId) {
        LambdaUpdateWrapper<SpuAttrDO> uw = new LambdaUpdateWrapper<>();
        uw.eq(SpuAttrDO::getSpuId, spuId);
        spuAttrMapper.delete(uw);
    }

    private void savePictures(Commodity aggregate, SpuDO spuDO) {
        if (aggregate.getId() != null) {
            LambdaUpdateWrapper<AttachmentDO> uw = new LambdaUpdateWrapper<>();
            uw.eq(AttachmentDO::getBizType, AttachmentBizType.SPU_PIC)
                    .eq(AttachmentDO::getBizId, spuDO.getId());
            attachmentMapper.delete(uw);
        }
        List<Picture> picList = aggregate.getPicList();
        if (CollectionUtils.isNotEmpty(picList)) {
            Long spuDOId = spuDO.getId();
            List<AttachmentDO> doList = picList.stream().map(item -> {
                AttachmentDO e = new AttachmentDO();
                e.setBizType(AttachmentBizType.SPU_PIC);
                e.setBizId(spuDOId);
                e.setUrl(item.getUrl());
                return e;
            }).collect(Collectors.toList());
            doList.forEach(attachmentMapper::insert);
        }
    }

    private void saveSalesInfo(SpuDO spuDO, Commodity aggregate) {
        SalesInfo salesInfo = aggregate.getSalesInfo();
        SpuSalesDO entity = new SpuSalesDO();
        entity.setSpuId(spuDO.getId());
        entity.setFreightTemplateId(salesInfo.getFreightTemplateId());
        entity.setPcDetailHtml(salesInfo.getPcDetailHtml());
        entity.setMobileDetailHtml(salesInfo.getMobileDetailHtml());
        entity.setParamData(JSONObject.toJSONString(salesInfo.getParamData()));
        if (aggregate.getId() != null) {
            spuSalesMapper.update(entity, new LambdaUpdateWrapper<SpuSalesDO>().eq(SpuSalesDO::getSpuId, spuDO.getId()));
        } else {
            spuSalesMapper.insert(entity);
        }
    }

    private void saveBaseInfo(SpuDO spuDO) {
        if (spuDO.getId() == null) {
            spuMapper.insert(spuDO);
        } else {
            spuMapper.updateById(spuDO);
        }
    }

    @Override
    public Commodity findById(Long aLong) {
        return null;
    }

    @Override
    public boolean update(Commodity aggregate) {
        return false;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }
}
