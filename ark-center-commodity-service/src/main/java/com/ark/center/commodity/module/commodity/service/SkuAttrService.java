package com.ark.center.commodity.module.commodity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SkuAttrDO;
import com.ark.center.commodity.infrastructure.commodity.repository.db.SkuAttrMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku规格属性 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Service
public class SkuAttrService extends ServiceImpl<SkuAttrMapper, SkuAttrDO> implements IService<SkuAttrDO> {

}
