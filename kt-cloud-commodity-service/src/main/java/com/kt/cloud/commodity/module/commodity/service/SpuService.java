package com.kt.cloud.commodity.module.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.dao.mapper.SpuMapper;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.component.web.util.bean.BeanConvertor;
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

    public IPage<CommodityPageRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        return lambdaQuery()
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CommodityPageRespDTO.class));
    }
}
