package com.ark.center.product.infra.product.service;

import com.ark.center.product.client.attr.dto.AttrOptionDTO;
import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.center.product.infra.attr.AttrOption;
import com.ark.center.product.infra.attr.repository.db.AttrOptionMapper;
import com.ark.center.product.infra.attr.gateway.impl.AttrService;
import com.ark.center.product.infra.product.repository.db.SpuAttrMapper;
import com.ark.center.product.infra.product.repository.db.SpuMapper;
import com.ark.center.product.infra.product.repository.db.SpuSalesMapper;
import com.ark.center.product.infra.spu.Spu;
import com.ark.center.product.infra.spu.SpuAttr;
import com.ark.center.product.infra.spu.SpuSales;
import com.ark.component.common.util.assemble.DataProcessor;
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
public class SpuService extends ServiceImpl<SpuMapper, Spu> {

    private final SpuSalesMapper spuSalesMapper;
    private final SpuAttrMapper spuAttrMapper;
    private final AttrOptionMapper attrOptionMapper;
    private final AttrService attrService;
    
    public Spu selectById(Long id) {
        return getById(id);
    }
    
    public Page<Spu> selectPages(GoodsQry queryDTO) {
        LambdaQueryWrapper<Spu> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(BaseEntity::getUpdateTime);
        return this.page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()), qw);
    }
    
    public void insert(Spu spu) {
        save(spu);
    }
    
    public void saveSpuSales(SpuSales spuSales) {
        spuSalesMapper.insert(spuSales);
    }
    
    public void insertAttrs(List<SpuAttr> spuAttrs) {
        spuAttrs.forEach(spuAttrMapper::insert);
    }
    
    public void saveAttrOptions(List<AttrOption> dos) {
        dos.forEach(attrOptionMapper::insert);
    }
    
    public void saveSpu(Spu spu) {
        save(spu);
        spu.getId();
    }
    
    public void updateSpu(Spu spu) {
        updateById(spu);
    }
    
    public List<SpuAttr> selectAttrsBySpuId(Long spuId) {
        LambdaQueryWrapper<SpuAttr> qw = new LambdaQueryWrapper<>();
        qw.select(SpuAttr::getId,
                        SpuAttr::getAttrId,
                        SpuAttr::getAttrValue)
                .eq(SpuAttr::getSpuId, spuId);
        return spuAttrMapper.selectList(qw);
    }
    
    public List<GoodsAttrDTO> selectSpecs(List<Long> spuIds) {
        return spuAttrMapper.selectSpuSpecs(spuIds);
    }
    
    public void batchDeleteAttrs(List<Long> records) {
        spuAttrMapper.deleteBatchIds(records);
    }
    
    public SpuSales selectSalesBySpuId(Long spuId) {
        LambdaQueryWrapper<SpuSales> qw = new LambdaQueryWrapper<>();
        qw.eq(SpuSales::getSpuId, spuId)
                .last("limit 1");
        return spuSalesMapper.selectOne(qw);
    }
    
    public List<SpuSales> selectSalesBySpuIds(List<Long> spuIds) {
        LambdaQueryWrapper<SpuSales> qw = new LambdaQueryWrapper<>();
        qw.in(SpuSales::getSpuId, spuIds);
        return spuSalesMapper.selectList(qw);
    }
    
    public List<Spu> selectByCategoryIds(List<Long> categoryIds) {
        return lambdaQuery()
                .in(Spu::getCategoryId, categoryIds)
                .list();
    }
    
    public boolean updateSpuSales(SpuSales sales) {
        LambdaUpdateWrapper<SpuSales> update = Wrappers.lambdaUpdate(SpuSales.class);
        update.eq(SpuSales::getSpuId, sales.getSpuId());
        return spuSalesMapper.update(sales, update) > 0;
    }

    /**
     * 查询Spu规格
     */
    public List<GoodsAttrDTO> querySpecWithOptions(List<Long> spuIds, AttrOption.Type optionType) {
        List<GoodsAttrDTO> goodsAttrDTOS = selectSpecs(spuIds);
        DataProcessor.create(goodsAttrDTOS)
                .keySelect(GoodsAttrDTO::getAttrId)
                .query(attrs -> attrService.selectOptions(attrs, spuIds, optionType))
                .keyBy(AttrOptionDTO::getAttrId)
                .collection()
                .process(GoodsAttrDTO::setOptionList);
        return goodsAttrDTOS;
    }

}
