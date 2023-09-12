package com.ark.center.commodity.infra.commodity.repository.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.attachment.Attachment;
import com.ark.center.commodity.domain.spu.*;
import com.ark.center.commodity.domain.spu.Sku;
import com.ark.center.commodity.domain.spu.aggregate.Commodity;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.center.commodity.domain.spu.vo.*;
import com.ark.center.commodity.infra.attachment.gateway.db.AttachmentMapper;
import com.ark.center.commodity.domain.attr.AttrOptionDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrOptionMapper;
import com.ark.center.commodity.infra.commodity.AttachmentBizType;
import com.ark.center.commodity.infra.commodity.convertor.CommodityConvertor;
import com.ark.center.commodity.infra.commodity.repository.db.*;
import com.ark.component.cache.CacheService;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpuGatewayImpl extends ServiceImpl<SpuMapper, Spu> implements SpuGateway {

    private final CommodityConvertor convertor;
    private final SkuMapper skuMapper;
    private final SpuSalesMapper spuSalesMapper;
    private final SkuAttrMapper skuAttrMapper;
    private final SpuAttrMapper spuAttrMapper;
    private final AttachmentMapper attachmentMapper;
    private final AttrOptionMapper attrOptionMapper;


    @Override
    public Spu selectById(Long id) {
        return selectById(id);
//        assembleSpuInfo(commodity, spu);
//        assembleSkuList(commodity, spu);
//        return commodity;
    }



    private void assembleSkuList(Commodity commodityRespDTO, Spu spu) {
        List<com.ark.center.commodity.domain.spu.vo.Sku> skuList = listSkuBySpuId(spu.getId());
        commodityRespDTO.setSkuList(skuList);

    }

    private List<com.ark.center.commodity.domain.spu.vo.Sku> listSkuBySpuId(Long id) {
        LambdaQueryWrapper<Sku> qw = new LambdaQueryWrapper<>();
        qw.eq(Sku::getSpuId, id);
        List<Sku> doList = skuMapper.selectList(qw);
        return convertor.convertToSku(doList);
    }

    private void assembleSpuInfo(Commodity commodity, Spu spu) {
        Long spuId = spu.getId();
        commodity.setId(spuId);
        commodity.setName(spu.getName());
        commodity.setCode(spu.getCode());
        commodity.setDescription(spu.getDescription());
        commodity.setBrandId(spu.getBrandId());
        commodity.setCategoryId(spu.getCategoryId());
        commodity.setMainPicture(spu.getMainPicture());
        commodity.setShelfStatus(Commodity.ShelfStatus.getByValue(spu.getShelfStatus()));
        commodity.setShowPrice(spu.getShowPrice());
        commodity.setUnit(spu.getUnit());
        commodity.setWeight(spu.getWeight());
        commodity.setPicList(findSpuPictures(spuId));
        commodity.setSalesInfo(findSpuSalesInfo(spuId));

    }

    private SalesInfo findSpuSalesInfo(Long spuId) {
        LambdaQueryWrapper<SpuSales> qw = new LambdaQueryWrapper<>();
        qw.eq(SpuSales::getSpuId, spuId);
        SpuSales spuSales = spuSalesMapper.selectOne(qw);
        return convertor.toSpuSales(spuSales);
    }

    private List<Picture> findSpuPictures(Long spuId) {
        List<Attachment> attachments =  listFileByBizTypeAndBizId(AttachmentBizType.SPU_PIC, spuId);
        if (CollectionUtils.isEmpty(attachments)) {
            return Collections.emptyList();
        }
        return attachments.stream().map(item -> {
            return new Picture(item.getUrl(), "");
        }).collect(Collectors.toList());

    }

    private List<Attachment> listFileByBizTypeAndBizId(String bizType, Long bizId) {
        LambdaQueryWrapper<Attachment> qw = new LambdaQueryWrapper<>();
        qw.eq(Attachment::getBizType, bizType)
                .eq(Attachment::getBizId, bizId);
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
    public IPage<CommodityPageDTO> selectPages(CommodityPageQry queryDTO) {
        LambdaQueryWrapper<Spu> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(BaseEntity::getGmtModified);
        Page<Spu> page = spuMapper.selectPage(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), qw);
        List<Spu> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        return page.convert(convertor::toCommodityPageDTO);
    }

    @Override
    public void insert(Spu spu) {
        save(spu);
    }

    @Override
    public void saveSales(SpuSales spuSales) {
        LambdaQueryWrapper<SpuSales> qw = new LambdaQueryWrapper<>();
        qw.select(BaseEntity::getId)
                .eq(SpuSales::getSpuId, spuSales.getSpuId());
        if (spuSalesMapper.selectOne(qw) != null) {
            spuSalesMapper.updateById(spuSales);
        } else {
            spuSalesMapper.insert(spuSales);
        }
    }

    @Override
    public void saveAttrs(List<SpuAttr> spuAttrs) {
        SpuAttr spuId = spuAttrs.get(0);
        LambdaQueryWrapper<SpuAttr> qw = new LambdaQueryWrapper<>();
        qw.select(SpuAttr::getId)
                .eq(SpuAttr::getSpuId, spuId);
        List<SpuAttr> records = spuAttrMapper.selectList(qw);
        // 如果spu原本存在数据，先删除
        if (records.size() > 0) {
            spuAttrMapper.deleteBatchIds(spuAttrs);
        }
        spuAttrs.forEach(spuAttrMapper::insert);
    }

    @Override
    public void saveAttrOptions(List<AttrOptionDO> dos) {
        dos.forEach(attrOptionMapper::insert);
    }

    @Override
    public Long saveSpu(Spu spu) {
        if (spu.getId() != null) {
            updateById(spu);
        } else {
            save(spu);
        }
        return spu.getId();
    }

}
