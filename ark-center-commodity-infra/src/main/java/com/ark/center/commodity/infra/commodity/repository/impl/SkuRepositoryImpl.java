package com.ark.center.commodity.infra.commodity.repository.impl;

import com.ark.center.commodity.client.commodity.dto.SkuDTO;
import com.ark.center.commodity.domain.commodity.repository.SkuRepository;
import com.ark.center.commodity.domain.commodity.vo.Sku;
import com.ark.center.commodity.infra.commodity.convertor.CommodityConvertor;
import com.ark.center.commodity.infra.commodity.repository.db.SkuDO;
import com.ark.center.commodity.infra.commodity.repository.db.SkuMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuRepositoryImpl extends ServiceImpl<SkuMapper, SkuDO> implements IService<SkuDO>, SkuRepository {

    @Override
    public Long store(Sku aggregate) {
        return null;
    }

    @Override
    public Sku findById(Long aLong) {
        return null;
    }

    @Override
    public boolean update(Sku aggregate) {
        return false;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public List<Sku> queryByIds(List<Long> ids) {
        List<SkuDO> skuList = listByIds(ids);
        return CommodityConvertor.convertToSku(skuList);
    }
}
