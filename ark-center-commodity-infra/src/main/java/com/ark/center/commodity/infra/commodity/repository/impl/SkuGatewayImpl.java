package com.ark.center.commodity.infra.commodity.repository.impl;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.spu.Sku;
import com.ark.center.commodity.domain.spu.SkuAttr;
import com.ark.center.commodity.domain.spu.assembler.SkuAssembler;
import com.ark.center.commodity.domain.spu.gateway.SkuGateway;
import com.ark.center.commodity.infra.commodity.repository.db.SkuAttrMapper;
import com.ark.center.commodity.infra.commodity.repository.db.SkuMapper;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public void deleteAttrsBySpuId(Long spuId) {
        List<Sku> skuList = lambdaQuery()
                .select(BaseEntity::getId)
                .eq(Sku::getSpuId, spuId)
                .list();
        List<Long> skuIds = skuList.stream().map(BaseEntity::getId).collect(Collectors.toList());

        LambdaQueryWrapper<SkuAttr> qw = new LambdaQueryWrapper<>();
        qw.select(BaseEntity::getId)
                        .in(SkuAttr::getSkuId, skuIds);
        List<SkuAttr> skuAttrs = skuAttrMapper.selectList(qw);

        if (CollectionUtils.isNotEmpty(skuAttrs)) {
            List<Long> ids = skuAttrs.stream().map(BaseEntity::getId).sorted().toList();
            skuAttrMapper.deleteBatchIds(ids);
        }
    }

    @Override
    public void update(Sku sku) {
        updateById(sku);
    }
}
