package com.kt.cloud.commodity.module.attr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.AttrDO;
import com.kt.cloud.commodity.dao.entity.AttrGroupDO;
import com.kt.cloud.commodity.dao.mapper.AttrMapper;
import com.kt.cloud.commodity.module.attr.dto.request.AttrCreateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.component.common.ParamsChecker;
import com.kt.component.dto.PageResponse;
import com.kt.component.exception.ExceptionFactory;
import com.kt.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class AttrService extends ServiceImpl<AttrMapper, AttrDO> implements IService<AttrDO> {

    private final AttrTemplateService attrTemplateService;
    private final AttrGroupService attrGroupService;

    public AttrService(AttrTemplateService attrTemplateService, AttrGroupService attrGroupService) {
        this.attrTemplateService = attrTemplateService;
        this.attrGroupService = attrGroupService;
    }

    public Long createAttr(AttrCreateReqDTO reqDTO) {
        checkBeforeCreate(reqDTO);
        AttrGroupDO attrGroupDO = attrGroupService.getById(reqDTO.getAttrGroupId());
        ParamsChecker.throwIfIsNull(attrGroupDO, ExceptionFactory.userException("属性分组不存在"));

        AttrDO entity = BeanConvertor.copy(reqDTO, AttrDO.class);
        entity.setAttrTemplateId(attrGroupDO.getAttrTemplateId());

        save(entity);
        return entity.getId();
    }

    private void checkBeforeCreate(AttrCreateReqDTO reqDTO) {
        if (reqDTO.getInputType().equals(AttrDO.InputType.SELECT.getValue())) {
            ParamsChecker.throwIfIsEmpty(reqDTO.getOptions(), ExceptionFactory.userException("请填写选项"));
        }
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
