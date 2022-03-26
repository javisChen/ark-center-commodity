package com.kt.cloud.commodity.module.attr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.AttrGroupDO;
import com.kt.cloud.commodity.dao.mapper.AttrGroupMapper;
import com.kt.cloud.commodity.module.attr.dto.request.AttrGroupCreateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrGroupUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrGroupPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrGroupRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

/**
 * <p>
 * 商品属性组 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class AttrGroupService extends ServiceImpl<AttrGroupMapper, AttrGroupDO> implements IService<AttrGroupDO> {

    private final AttrTemplateService attrTemplateService;

    public AttrGroupService(AttrTemplateService attrTemplateService) {
        this.attrTemplateService = attrTemplateService;
    }

    public Long createAttrGroup(AttrGroupCreateReqDTO reqDTO) {
        attrTemplateService.checkTemplateExists(reqDTO.getAttrTemplateId());
        AttrGroupDO entity = BeanConvertor.copy(reqDTO, AttrGroupDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<AttrGroupRespDTO> getPageList(AttrGroupPageQueryReqDTO queryDTO) {
        IPage<AttrGroupRespDTO> page = lambdaQuery()
                .eq(AttrGroupDO::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrGroupRespDTO.class));
        return BeanConvertor.copyPage(page, AttrGroupRespDTO.class);
    }

    public Long updateAttrGroup(AttrGroupUpdateReqDTO reqDTO) {
        attrTemplateService.checkTemplateExists(reqDTO.getAttrTemplateId());
        AttrGroupDO entity = BeanConvertor.copy(reqDTO, AttrGroupDO.class);
        updateById(entity);
        return entity.getId();
    }

    public AttrGroupRespDTO getAttrGroupInfo(Long AttrGroupId) {
        AttrGroupDO entity = getById(AttrGroupId);
        return BeanConvertor.copy(entity, AttrGroupRespDTO.class);
    }

}
