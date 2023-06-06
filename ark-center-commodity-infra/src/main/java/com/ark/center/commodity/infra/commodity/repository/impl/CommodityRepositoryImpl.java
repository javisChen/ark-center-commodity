package com.ark.center.commodity.infra.commodity.repository.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.commodity.aggregate.Commodity;
import com.ark.center.commodity.domain.commodity.repository.CommodityRepository;
import com.ark.center.commodity.domain.commodity.vo.*;
import com.ark.center.commodity.infra.attr.repository.db.AttrOptionDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrOptionMapper;
import com.ark.center.commodity.infra.commodity.AttachmentBizType;
import com.ark.center.commodity.infra.commodity.convertor.CommodityConvertor;
import com.ark.center.commodity.infra.commodity.repository.cache.CommodityCacheConst;
import com.ark.center.commodity.infra.commodity.repository.db.*;
import com.ark.component.cache.CacheService;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommodityRepositoryImpl implements CommodityRepository {

    private final CommodityConvertor convertor;
    private final SpuMapper spuMapper;
    private final SkuMapper skuMapper;

    private final SpuSalesMapper spuSalesMapper;

    private final SkuAttrMapper skuAttrMapper;
    private final SpuAttrMapper spuAttrMapper;
    private final AttachmentMapper attachmentMapper;
    private final AttrOptionMapper attrOptionMapper;

    private final CacheService cacheService;

    @Override
    public Long store(Commodity aggregate) {
        SpuDO spuDO = saveSpu(aggregate);
        saveSku(aggregate, spuDO);
        return spuDO.getId();
    }

    private void saveSku(Commodity aggregate, SpuDO spuDO) {
        Long spuId = spuDO.getId();
        if (aggregate.getId() != null && aggregate.getFlushSku()) {
            // todo 把当前的SKU记录到历史表里面
            removeSku(spuId);
            removeSkuAttrs(spuId);
        }
        List<Sku> skuList = aggregate.getSkuList();

        Map<String, Object> stockMap = new HashMap<>(skuList.size());
        for (Sku sku : skuList) {
            SkuDO skuDO = convertor.convertToSkuDO(spuDO, aggregate.getPicList().get(0), sku);
            if (aggregate.getId() != null && !aggregate.getFlushSku()) {
                skuMapper.updateById(skuDO);
            } else {
                skuMapper.insert(skuDO);
            }
            saveSkuAttrs(skuDO.getId(), sku.getSpecList());

            stockMap.put(CommodityCacheConst.REDIS_SKU_STOCK + sku.getId(), sku.getStock());
        }

        // 批量缓存SKU库存
        cacheService.multiSet(stockMap);
    }

    private void saveSkuAttrs(Long skuId, List<Attr> specList) {
        List<SkuAttrDO> doList = specList.stream().map(item -> {
            SkuAttrDO skuAttrDO = new SkuAttrDO();
            skuAttrDO.setSkuId(skuId);
            skuAttrDO.setAttrValue(item.getAttrValue());
            return skuAttrDO;
        }).collect(Collectors.toList());
        doList.forEach(skuAttrMapper::insert);
    }

    private void removeSku(Long spuId) {
        skuMapper.delete(new LambdaUpdateWrapper<SkuDO>().eq(SkuDO::getSpuId, spuId));
    }

    private void removeSkuAttrs(Long spuId) {
        LambdaQueryWrapper<SkuDO> qw = new LambdaQueryWrapper<>();
        qw.eq(SkuDO::getSpuId, spuId);
        List<SkuDO> skuDOList = skuMapper.selectList(qw);
        List<Long> skuIds = skuDOList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        skuAttrMapper.deleteBatchIds(skuIds);
    }

    private SpuDO saveSpu(Commodity aggregate) {
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

        return spuDO;
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
    public Commodity findById(Long id) {
        SpuDO spuDO = spuMapper.selectById(id);
        Commodity commodity = convertor.toAggregate(spuDO);
        assembleSpuInfo(commodity, spuDO);
        assembleSkuList(commodity, spuDO);
        return commodity;
    }



    private void assembleSkuList(Commodity commodityRespDTO, SpuDO spuDO) {
        List<Sku> skuList = listSkuBySpuId(spuDO.getId());
        commodityRespDTO.setSkuList(skuList);

    }

    private List<Sku> listSkuBySpuId(Long id) {
        LambdaQueryWrapper<SkuDO> qw = new LambdaQueryWrapper<>();
        qw.eq(SkuDO::getSpuId, id);
        List<SkuDO> doList = skuMapper.selectList(qw);
        return convertor.convertToSku(doList);
    }

    private void assembleSpuInfo(Commodity commodity, SpuDO spuDO) {
        Long spuId = spuDO.getId();
        commodity.setId(spuId);
        commodity.setName(spuDO.getName());
        commodity.setCode(spuDO.getCode());
        commodity.setDescription(spuDO.getDescription());
        commodity.setBrandId(spuDO.getBrandId());
        commodity.setCategoryId(spuDO.getCategoryId());
        commodity.setMainPicture(spuDO.getMainPicture());
        commodity.setShelfStatus(Commodity.ShelfStatus.getByValue(spuDO.getShelfStatus()));
        commodity.setShowPrice(spuDO.getShowPrice());
        commodity.setUnit(spuDO.getUnit());
        commodity.setWeight(spuDO.getWeight());
        commodity.setPicList(findSpuPictures(spuId));
        commodity.setSalesInfo(findSpuSalesInfo(spuId));

    }

    private SalesInfo findSpuSalesInfo(Long spuId) {
        LambdaQueryWrapper<SpuSalesDO> qw = new LambdaQueryWrapper<>();
        qw.eq(SpuSalesDO::getSpuId, spuId);
        SpuSalesDO spuSalesDO = spuSalesMapper.selectOne(qw);
        return convertor.toSpuSales(spuSalesDO);
    }

    private List<Picture> findSpuPictures(Long spuId) {
        List<AttachmentDO> attachmentDOS =  listFileByBizTypeAndBizId(AttachmentBizType.SPU_PIC, spuId);
        if (CollectionUtils.isEmpty(attachmentDOS)) {
            return Collections.emptyList();
        }
        return attachmentDOS.stream().map(item -> {
            return new Picture(item.getUrl(), "");
        }).collect(Collectors.toList());

    }

    private List<AttachmentDO> listFileByBizTypeAndBizId(String bizType, Long bizId) {
        LambdaQueryWrapper<AttachmentDO> qw = new LambdaQueryWrapper<>();
        qw.eq(AttachmentDO::getBizType, bizType)
                .eq(AttachmentDO::getBizId, bizId);
        return attachmentMapper.selectList(qw);
    }

    @Override
    public boolean update(Commodity aggregate) {
        return false;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public List<Commodity> queryByIds(List<Long> ids) {
        return null;
    }

    @Override
    public IPage<Commodity> getPageList(CommodityPageQry queryDTO) {
        LambdaQueryWrapper<SpuDO> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(BaseEntity::getGmtModified);
        Page<SpuDO> page = spuMapper.selectPage(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), qw);
        List<SpuDO> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        return page.convert(convertor::toAggregate);
    }
}
