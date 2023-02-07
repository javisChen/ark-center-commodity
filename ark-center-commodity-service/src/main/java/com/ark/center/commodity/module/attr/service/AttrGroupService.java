package com.ark.center.commodity.module.attr.service;

import com.ark.center.commodity.infra.attr.repository.db.AttrGroupDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrGroupMapper;
import com.ark.center.commodity.infra.attr.repository.db.AttrGroupRelDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrGroupRelMapper;
import com.ark.center.commodity.infra.category.repository.db.CategoryDO;
import com.ark.center.commodity.module.attr.dto.request.AttrGroupCreateReqDTO;
import com.ark.center.commodity.module.attr.dto.request.AttrGroupPageQueryReqDTO;
import com.ark.center.commodity.module.attr.dto.request.AttrGroupUpdateReqDTO;
import com.ark.center.commodity.module.attr.dto.response.AttrGroupRespDTO;
import com.ark.center.commodity.module.category.service.CategoryAdminService;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private final AttrGroupRelMapper attrGroupRelMapper;
    private final CategoryAdminService categoryAdminService;

    public AttrGroupService(AttrTemplateService attrTemplateService,
                            AttrGroupRelMapper attrGroupRelMapper,
                            CategoryAdminService categoryAdminService) {
        this.attrTemplateService = attrTemplateService;
        this.attrGroupRelMapper = attrGroupRelMapper;
        this.categoryAdminService = categoryAdminService;
    }

    public Long createAttrGroup(AttrGroupCreateReqDTO reqDTO) {
        attrTemplateService.checkTemplateExists(reqDTO.getAttrTemplateId());
        AttrGroupDO entity = BeanConvertor.copy(reqDTO, AttrGroupDO.class);
        save(entity);
        return entity.getId();
    }

    public IPage<AttrGroupRespDTO> getPageList(AttrGroupPageQueryReqDTO queryDTO) {
        if (queryDTO.getCategoryId() != null) {
            CategoryDO categoryDO = Optional.ofNullable(categoryAdminService.getById(queryDTO.getCategoryId()))
                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(categoryDO.getAttrTemplateId());
        }
        return lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrGroupDO::getName, queryDTO.getName())
                .eq(AttrGroupDO::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrGroupRespDTO.class));
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

    public void saveAttrGroupRel(Long attrGroupId, Long attrId) {
        AttrGroupRelDO entity = new AttrGroupRelDO();
        entity.setAttrId(attrId);
        entity.setAttrGroupId(attrGroupId);
        attrGroupRelMapper.insert(entity);
    }

    public Long countById(Long attrGroupId) {
        if (attrGroupId == null) {
            return 0L;
        }
        return lambdaQuery().eq(BaseEntity::getId, attrGroupId).count();
    }
}
