package com.kt.cloud.commodity.module.spu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.dao.mapper.SpuMapper;
import com.kt.cloud.commodity.module.spu.dto.request.SpuCreateReqDTO;
import com.kt.cloud.commodity.module.spu.dto.request.SpuUpdateReqDTO;
import com.kt.cloud.commodity.module.spu.dto.request.SpuPageQueryReqDTO;
import com.kt.cloud.commodity.module.spu.dto.response.SpuRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

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

    public Long createSpu(SpuCreateReqDTO reqDTO) {
        SpuDO entity = BeanConvertor.copy(reqDTO, SpuDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<SpuRespDTO> getPageList(SpuPageQueryReqDTO queryDTO) {
        IPage<SpuRespDTO> page = lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, SpuRespDTO.class));
        return BeanConvertor.copyPage(page, SpuRespDTO.class);
    }

    public Long updateSpu(SpuUpdateReqDTO reqDTO) {
        SpuDO entity = BeanConvertor.copy(reqDTO, SpuDO.class);
        updateById(entity);
        return entity.getId();
    }

    public SpuRespDTO getSpuInfo(Long SpuId) {
        SpuDO entity = getById(SpuId);
        return BeanConvertor.copy(entity, SpuRespDTO.class);
    }

}
