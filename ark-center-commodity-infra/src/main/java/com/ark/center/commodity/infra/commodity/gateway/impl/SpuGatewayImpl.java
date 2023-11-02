package com.ark.center.commodity.infra.commodity.gateway.impl;

import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.client.commodity.dto.CommodityPageDTO;
import com.ark.center.commodity.client.commodity.query.CommodityPageQry;
import com.ark.center.commodity.domain.attr.AttrOption;
import com.ark.center.commodity.domain.spu.Spu;
import com.ark.center.commodity.domain.spu.SpuAttr;
import com.ark.center.commodity.domain.spu.SpuSales;
import com.ark.center.commodity.domain.spu.gateway.SpuGateway;
import com.ark.center.commodity.infra.attr.gateway.db.AttrOptionMapper;
import com.ark.center.commodity.infra.commodity.convertor.SpuConverter;
import com.ark.center.commodity.infra.commodity.gateway.db.SpuAttrMapper;
import com.ark.center.commodity.infra.commodity.gateway.db.SpuMapper;
import com.ark.center.commodity.infra.commodity.gateway.db.SpuSalesMapper;
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

    private final SpuConverter spuConverter;
    private final SpuSalesMapper spuSalesMapper;
    private final SpuAttrMapper spuAttrMapper;
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
        return page.convert(spuConverter::toCommodityPageDTO);
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
    public List<AttrDTO> selectAttrsBySpuId(Long spuId) {
        List<AttrDTO> attrDTOS = spuAttrMapper.selectBySpuId(spuId);
        return attrDTOS;
    }

    @Override
    public void batchDeleteAttrs(List<Long> records) {
        spuAttrMapper.deleteBatchIds(records);
    }

    @Override
    public SpuSales selectSalesBySpuId(Long spuId) {
        LambdaQueryWrapper<SpuSales> qw = new LambdaQueryWrapper<>();
        qw.eq(SpuSales::getSpuId, spuId)
                .last("limit 1");
        return spuSalesMapper.selectOne(qw);
    }

    @Override
    public List<Spu> selectByCategoryIds(List<Long> categoryIds) {
        return lambdaQuery()
                .in(Spu::getCategoryId, categoryIds)
                .list();
    }

}
