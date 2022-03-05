package com.kt.cloud.commodity.module.attrspu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.AttrSpuDO;
import com.kt.cloud.commodity.dao.mapper.AttrSpuMapper;
import com.kt.cloud.commodity.module.attrspu.dto.request.AttrSpuCreateReqDTO;
import com.kt.cloud.commodity.module.attrspu.dto.request.AttrSpuUpdateReqDTO;
import com.kt.cloud.commodity.module.attrspu.dto.request.AttrSpuPageQueryReqDTO;
import com.kt.cloud.commodity.module.attrspu.dto.response.AttrSpuRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

/**
 * <p>
 * SPU特有的商品属性选项 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class AttrSpuService extends ServiceImpl<AttrSpuMapper, AttrSpuDO> implements IService<AttrSpuDO> {

    public Long createAttrSpu(AttrSpuCreateReqDTO reqDTO) {
        AttrSpuDO entity = BeanConvertor.copy(reqDTO, AttrSpuDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<AttrSpuRespDTO> getPageList(AttrSpuPageQueryReqDTO queryDTO) {
        IPage<AttrSpuRespDTO> page = lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrSpuRespDTO.class));
        return BeanConvertor.copyPage(page, AttrSpuRespDTO.class);
    }

    public Long updateAttrSpu(AttrSpuUpdateReqDTO reqDTO) {
        AttrSpuDO entity = BeanConvertor.copy(reqDTO, AttrSpuDO.class);
        updateById(entity);
        return entity.getId();
    }

    public AttrSpuRespDTO getAttrSpuInfo(Long AttrSpuId) {
        AttrSpuDO entity = getById(AttrSpuId);
        return BeanConvertor.copy(entity, AttrSpuRespDTO.class);
    }

}
