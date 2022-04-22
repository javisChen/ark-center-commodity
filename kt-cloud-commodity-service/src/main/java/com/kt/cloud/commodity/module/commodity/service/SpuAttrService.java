package com.kt.cloud.commodity.module.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.SpuAttrDO;
import com.kt.cloud.commodity.dao.mapper.SpuAttrMapper;
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

}
