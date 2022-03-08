package com.kt.cloud.commodity.module.attr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.AttrDO;
import com.kt.cloud.commodity.dao.mapper.AttrMapper;
import com.kt.cloud.commodity.module.attr.dto.request.AttrCreateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Service
public class AttrService extends ServiceImpl<AttrMapper, AttrDO> implements IService<AttrDO> {

    public Long createAttr(AttrCreateReqDTO reqDTO) {
        AttrDO entity = BeanConvertor.copy(reqDTO, AttrDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<AttrRespDTO> getPageList(AttrPageQueryReqDTO queryDTO) {
        IPage<AttrRespDTO> page = lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrRespDTO.class));
        return BeanConvertor.copyPage(page, AttrRespDTO.class);
    }

    public Long updateAttr(AttrUpdateReqDTO reqDTO) {
        AttrDO entity = BeanConvertor.copy(reqDTO, AttrDO.class);
        updateById(entity);
        return entity.getId();
    }

    public AttrRespDTO getAttrInfo(Long AttrId) {
        AttrDO entity = getById(AttrId);
        return BeanConvertor.copy(entity, AttrRespDTO.class);
    }

}
