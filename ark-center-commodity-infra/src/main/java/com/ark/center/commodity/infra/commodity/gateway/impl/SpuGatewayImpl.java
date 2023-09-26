package com.ark.center.commodity.infra.commodity.gateway.impl;

import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.attr.AttrOption;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuAttr;
import com.ark.center.commodity.domain.spu.SpuSales;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.center.commodity.infra.attachment.gateway.db.AttachmentMapper;
import com.ark.center.commodity.infra.attr.gateway.db.AttrOptionMapper;
import com.ark.center.commodity.infra.commodity.convertor.SpuConverter;
import com.ark.center.commodity.infra.commodity.gateway.db.*;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpuGatewayImpl extends ServiceImpl<SpuMapper, Spu> implements SpuGateway {

    private final SpuConverter convertor;
    private final SkuMapper skuMapper;
    private final SpuSalesMapper spuSalesMapper;
    private final SkuAttrMapper skuAttrMapper;
    private final SpuAttrMapper spuAttrMapper;
    private final AttachmentMapper attachmentMapper;
    private final AttrOptionMapper attrOptionMapper;


    @Override
    public Spu selectById(Long id) {
        return getById(id);
    }

    @Override
    public IPage<CommodityPageDTO> selectPages(CommodityPageQry queryDTO) {
        LambdaQueryWrapper<Spu> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(BaseEntity::getGmtModified);
        Page<Spu> page = this.page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), qw);
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
    public void insertAttrs(List<SpuAttr> spuAttrs) {
        spuAttrs.forEach(spuAttrMapper::insert);
    }

    @Override
    public void saveAttrOptions(List<AttrOption> dos) {
        dos.forEach(attrOptionMapper::insert);
    }

    @Override
    public Long saveSpu(Spu spu) {
        save(spu);
        return spu.getId();
    }

    @Override
    public boolean updateSpu(Spu spu) {
        return updateById(spu);
    }

    @Override
    public List<SpuAttr> selectAttrsBySpuId(Long spuId) {
        LambdaQueryWrapper<SpuAttr> qw = new LambdaQueryWrapper<>();
        qw.select(SpuAttr::getId)
                .eq(SpuAttr::getSpuId, spuId);
        List<SpuAttr> records = spuAttrMapper.selectList(qw);
        return records;
    }

    @Override
    public void batchDeleteAttrs(List<SpuAttr> records) {
        List<Long> ids = records.stream().map(BaseEntity::getId).toList();
        spuAttrMapper.deleteBatchIds(ids);
    }

}
