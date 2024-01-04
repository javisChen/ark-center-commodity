package com.ark.center.product.infra.product.gateway.impl;

import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.center.product.domain.attr.AttrOption;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.SpuAttr;
import com.ark.center.product.domain.spu.SpuSales;
import com.ark.center.product.domain.spu.assembler.SpuAssembler;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.center.product.infra.attr.gateway.db.AttrOptionMapper;
import com.ark.center.product.infra.product.gateway.db.SpuAttrMapper;
import com.ark.center.product.infra.product.gateway.db.SpuMapper;
import com.ark.center.product.infra.product.gateway.db.SpuSalesMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpuGatewayImpl extends ServiceImpl<SpuMapper, Spu> implements SpuGateway {

    private final SpuAssembler spuAssembler;
    private final SpuSalesMapper spuSalesMapper;
    private final SpuAttrMapper spuAttrMapper;
    private final AttrOptionMapper attrOptionMapper;

    @Override
    public Spu selectById(Long id) {
        return getById(id);
    }

    @Override
    public Page<Spu> selectPages(GoodsQry queryDTO) {
        LambdaQueryWrapper<Spu> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(BaseEntity::getUpdateTime);
        return this.page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), qw);
    }

    @Override
    public void insert(Spu spu) {
        save(spu);
    }

    @Override
    public void saveSpuSales(SpuSales spuSales) {
        spuSalesMapper.insert(spuSales);
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
    public void saveSpu(Spu spu) {
        save(spu);
        spu.getId();
    }

    @Override
    public void updateSpu(Spu spu) {
        updateById(spu);
    }

    @Override
    public List<SpuAttr> selectAttrsBySpuId(Long spuId) {
        LambdaQueryWrapper<SpuAttr> qw = new LambdaQueryWrapper<>();
        qw.select(SpuAttr::getId,
                        SpuAttr::getAttrId,
                        SpuAttr::getAttrValue)
                .eq(SpuAttr::getSpuId, spuId);
        return spuAttrMapper.selectList(qw);
    }

    @Override
    public List<GoodsAttrDTO> selectSpecs(List<Long> spuIds) {
        return spuAttrMapper.selectSpuSpecs(spuIds);
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
    public List<SpuSales> selectSalesBySpuIds(List<Long> spuIds) {
        LambdaQueryWrapper<SpuSales> qw = new LambdaQueryWrapper<>();
        qw.in(SpuSales::getSpuId, spuIds);
        return spuSalesMapper.selectList(qw);
    }

    @Override
    public List<Spu> selectByCategoryIds(List<Long> categoryIds) {
        return lambdaQuery()
                .in(Spu::getCategoryId, categoryIds)
                .list();
    }

    @Override
    public boolean updateSpuSales(SpuSales sales) {
        LambdaUpdateWrapper<SpuSales> update = Wrappers.lambdaUpdate(SpuSales.class);
        update.eq(SpuSales::getSpuId, sales.getSpuId());
        return spuSalesMapper.update(sales, update) > 0;
    }

}
