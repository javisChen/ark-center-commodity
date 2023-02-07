package com.ark.center.commodity.module.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ark.center.commodity.infra.commodity.repository.db.SpuSalesDO;
import com.ark.center.commodity.infra.commodity.repository.db.SpuSalesMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * spu参数属性 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Service
public class SpuSalesService extends ServiceImpl<SpuSalesMapper, SpuSalesDO> implements IService<SpuSalesDO> {

    public SpuSalesDO getBySpuId(Long spuId) {
        return lambdaQuery()
                .eq(SpuSalesDO::getSpuId, spuId)
                .one();
    }
}
