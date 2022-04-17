package com.kt.cloud.commodity.module.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.SkuDO;
import com.kt.cloud.commodity.dao.mapper.SkuMapper;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.SkuPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.SkuUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.SkuRespDTO;
import com.kt.component.dto.PageResponse;
import com.kt.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class SkuService extends ServiceImpl<SkuMapper, SkuDO> implements IService<SkuDO> {

    public Long createSku(CommodityUpdateReqDTO reqDTO) {
        SkuDO entity = BeanConvertor.copy(reqDTO, SkuDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<SkuRespDTO> getPageList(SkuPageQueryReqDTO queryDTO) {
        IPage<SkuRespDTO> page = lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, SkuRespDTO.class));
        return BeanConvertor.copyPage(page, SkuRespDTO.class);
    }

    public Long updateSku(SkuUpdateReqDTO reqDTO) {
        SkuDO entity = BeanConvertor.copy(reqDTO, SkuDO.class);
        updateById(entity);
        return entity.getId();
    }

    public SkuRespDTO getSkuInfo(Long SkuId) {
        SkuDO entity = getById(SkuId);
        return BeanConvertor.copy(entity, SkuRespDTO.class);
    }

}
