package com.kt.cloud.commodity.module.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.dao.mapper.SpuMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * spu主表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class SpuService extends ServiceImpl<SpuMapper, SpuDO> implements IService<SpuDO> {

}
