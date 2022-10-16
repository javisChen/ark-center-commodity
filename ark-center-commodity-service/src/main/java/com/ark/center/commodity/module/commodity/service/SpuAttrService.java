package com.ark.center.commodity.module.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ark.center.commodity.infrastructure.db.dataobject.SpuAttrDO;
import com.ark.center.commodity.infrastructure.db.mapper.SpuAttrMapper;
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
public class SpuAttrService extends ServiceImpl<SpuAttrMapper, SpuAttrDO> implements IService<SpuAttrDO> {

    public void removeBySpuId(Long spuId) {
        lambdaUpdate()
                .eq(SpuAttrDO::getSpuId, spuId)
                .remove();
    }
}
