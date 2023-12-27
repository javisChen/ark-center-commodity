package com.ark.center.product.infra.product.gateway.impl;

import com.ark.center.product.client.goods.dto.SkuDTO;
import com.ark.center.product.domain.sku.Sku;
import com.ark.center.product.domain.sku.SkuAttr;
import com.ark.center.product.domain.sku.assembler.SkuAssembler;
import com.ark.center.product.domain.sku.gateway.SkuGateway;
import com.ark.center.product.infra.product.gateway.db.SkuAttrMapper;
import com.ark.center.product.infra.product.gateway.db.SkuMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SkuGatewayImpl extends ServiceImpl<SkuMapper, Sku> implements IService<Sku>, SkuGateway {

    private final SkuAssembler skuAssembler;
    private final SkuAttrMapper skuAttrMapper;

    @Override
    public List<Sku> queryByIds(List<Long> ids) {
        return listByIds(ids);
    }

    @Override
    public List<SkuDTO> selectBySpuId(Long spuId) {
        List<Sku> skuList = lambdaQuery()
                .eq(Sku::getSpuId, spuId)
                .list();
        return skuAssembler.toSkuDTO(skuList);
    }

    @Override
    public List<SkuDTO> selectBySpuIds(List<Long> spuIds) {
        List<Sku> skuList = lambdaQuery()
                .in(Sku::getSpuId, spuIds)
                .list();
        return skuAssembler.toSkuDTO(skuList);
    }

    @Override
    public void insert(Sku sku) {
        save(sku);
    }

    @Override
    public void saveAttrs(List<SkuAttr> attrs) {

    }

    @Override
    public void deleteBySpuId(Long spuId) {
        List<Sku> records = lambdaQuery()
                .select(BaseEntity::getId)
                .eq(Sku::getSpuId, spuId)
                .list();
        if (CollectionUtils.isNotEmpty(records)) {
            removeByIds(records);
        }
    }

    @Override
    public void deleteAttrsBySpuId(Long spuId, List<SkuDTO> originalSkus) {
        List<Sku> skuList = lambdaQuery()
                .select(BaseEntity::getId)
                .eq(Sku::getSpuId, spuId)
                .list();
        if (CollectionUtils.isEmpty(skuList)) {
            return;
        }

        List<Long> skuIds = skuList.stream().map(BaseEntity::getId).sorted().collect(Collectors.toList());

        deleteAttrsBySkuIds(skuIds);
    }

    @Override
    public void deleteAttrsBySkuIds(List<Long> skuIds) {
        LambdaQueryWrapper<SkuAttr> qw = Wrappers.lambdaQuery(SkuAttr.class)
                .select(BaseEntity::getId)
                .in(SkuAttr::getSkuId, skuIds);
        List<SkuAttr> skuAttrs = skuAttrMapper.selectList(qw);
        if (CollectionUtils.isEmpty(skuAttrs)) {
            return;
        }
        List<Long> ids = skuAttrs.stream().map(BaseEntity::getId).sorted().toList();
        skuAttrMapper.deleteBatchIds(ids);
    }

    @Override
    public void deleteBySkuIds(List<Long> skuIds) {
        removeByIds(skuIds);
    }

    @Override
    public void update(Sku sku) {
        updateById(sku);
    }
}
