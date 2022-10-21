package com.ark.center.commodity.module.attr.service;

import cn.hutool.core.collection.CollUtil;
import com.ark.center.commodity.infrastructure.category.repository.db.CategoryDO;
import com.ark.center.commodity.infrastructure.db.dataobject.*;
import com.ark.center.commodity.module.attr.dto.request.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ark.center.commodity.infrastructure.db.mapper.AttrMapper;
import com.ark.center.commodity.module.attr.dto.response.AttrGroupRespDTO;
import com.ark.center.commodity.module.attr.dto.response.AttrRespDTO;
import com.ark.center.commodity.module.attr.support.AttrHelper;
import com.ark.center.commodity.module.category.service.CategoryAdminService;
import com.ark.component.common.ParamsChecker;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final AttrGroupService attrGroupService;
    private final AttrHelper attrHelper;
    private final AttrOptionService attrOptionService;
    private final AttrTemplateService attrTemplateService;
    private final CategoryAdminService categoryAdminService;

    public AttrService(AttrGroupService attrGroupService,
                       AttrHelper attrHelper,
                       AttrOptionService attrOptionService,
                       AttrTemplateService attrTemplateService,
                       CategoryAdminService categoryAdminService) {
        this.attrGroupService = attrGroupService;
        this.attrHelper = attrHelper;
        this.attrOptionService = attrOptionService;
        this.attrTemplateService = attrTemplateService;
        this.categoryAdminService = categoryAdminService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long createAttr(AttrUpdateReqDTO reqDTO) {
        if (reqDTO.getId() != null) {
            return updateAttr(reqDTO);
        }
        doCheck(reqDTO);
        AttrDO attrDO = BeanConvertor.copy(reqDTO, AttrDO.class);
        // 保存属性基本信息
        save(attrDO);
        // 保存属性值
        saveValues(attrDO, reqDTO.getValues());
        return attrDO.getId();
    }

    public PageResponse<AttrRespDTO> getPageList(AttrPageQueryReqDTO queryDTO) {
        if (queryDTO.getCategoryId() != null) {
            CategoryDO categoryDO = Optional.ofNullable(categoryAdminService.getById(queryDTO.getCategoryId()))
                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(categoryDO.getAttrTemplateId());
        }
        IPage<AttrRespDTO> page = getAttrPage(queryDTO);
        List<AttrRespDTO> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(page);
        }
        List<Long> attrIdList = records.stream().map(AttrRespDTO::getId).collect(Collectors.toList());
        if (queryDTO.getWithOptions()) {
            List<AttrOptionDO> attrOptionDOList = attrOptionService.listByAttrIdsAndType(attrIdList, AttrOptionDO.Type.COMMON.getValue());
            // 如果商品ID指明了，就把专属的属性项查出来
            if (queryDTO.getSpuId() != null) {
                attrOptionDOList.addAll(attrOptionService.listBySpuId(queryDTO.getSpuId()));
            }
            attrHelper.fillAttrValues(records, attrOptionDOList);
        }
        return PageResponse.of(page);
    }

    private IPage<AttrRespDTO> getAttrPage(AttrPageQueryReqDTO queryDTO) {
        return lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getAttrTemplateId()), AttrDO::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .eq(Objects.nonNull(queryDTO.getType()), AttrDO::getType, queryDTO.getType())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrDO::getName, queryDTO.getName())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrRespDTO.class));
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateAttr(AttrUpdateReqDTO reqDTO) {
        doCheck(reqDTO);

        Long attrId = reqDTO.getId();

        AttrDO attrNewDO = BeanConvertor.copy(reqDTO, AttrDO.class);
        updateById(attrNewDO);

        // 尝试清空属性值
        attemptRemoveAttrValues(attrId, attrNewDO);

        // 保存属性值
        saveValues(attrNewDO, reqDTO.getValues());
        return attrId;
    }

    private void doCheck(AttrUpdateReqDTO reqDTO) {
        Long count = attrTemplateService.countById(reqDTO.getAttrTemplateId());
        if (count.equals(0L)) {
            throw ExceptionFactory.userException("属性模板不存在");
        }
        Long attrGroupId = reqDTO.getAttrGroupId();
        if (attrGroupId != null && attrGroupId > 0) {
            count = attrGroupService.countById(attrGroupId);
            if (count.equals(0L)) {
                throw ExceptionFactory.userException("属性组不存在");
            }
        }
        Long attrId = reqDTO.getId();
        if (attrId != null && attrId > 0L) {
            AttrDO attrOldDO = getById(attrId);
            ParamsChecker.throwIfIsNull(attrOldDO, ExceptionFactory.userException("属性不存在"));
        }
    }

    private void attemptRemoveAttrValues(Long attrId, AttrDO attrNewDO) {
        // 如果录入方式为[SELECT]，把attr_value先清掉
        attrOptionService.removeByAttrId(attrId);
    }

    private void saveValues(AttrDO attrDO, List<String> values) {
        if (attrDO.getInputType().equals(AttrDO.InputType.SELECT.getValue())) {
            ParamsChecker.throwIfIsEmpty(values, ExceptionFactory.userException("属性值选项不能为空"));
            attrOptionService.batchSave(attrDO.getId(), values, AttrOptionDO.Type.COMMON.getValue());
        }
    }

    public AttrRespDTO getAttrInfo(Long attrId) {
        AttrDO entity = getById(attrId);
        AttrRespDTO respDTO = BeanConvertor.copy(entity, AttrRespDTO.class);
        List<AttrOptionDO> attrOptionDOList = attrOptionService.listByAttrId(attrId);
        if (CollUtil.isNotEmpty(attrOptionDOList)) {
            attrHelper.fillAttrValues(respDTO, attrOptionDOList);
        }
        return respDTO;
    }

    public Map<Long, List<AttrDO>> listByGroupIds(List<Long> groupIds) {
        List<AttrDO> list = lambdaQuery()
                .eq(AttrDO::getType, AttrDO.Type.PARAM.getValue())
                .in(AttrDO::getAttrGroupId, groupIds)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().collect(Collectors.groupingBy(AttrDO::getAttrGroupId));
    }

    public Long createAttrGroup(AttrGroupCreateReqDTO reqDTO) {
        attrTemplateService.checkTemplateExists(reqDTO.getAttrTemplateId());
        AttrGroupDO entity = BeanConvertor.copy(reqDTO, AttrGroupDO.class);
        attrGroupService.save(entity);
        return entity.getId();
    }

    public PageResponse<AttrGroupRespDTO> getAttrGroupPageList(AttrGroupPageQueryReqDTO queryDTO) {
        if (queryDTO.getCategoryId() != null) {
            CategoryDO categoryDO = Optional.ofNullable(categoryAdminService.getById(queryDTO.getCategoryId()))
                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(categoryDO.getAttrTemplateId());
        }
        IPage<AttrGroupRespDTO> page = attrGroupService.getPageList(queryDTO);
        List<AttrGroupRespDTO> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(page);
        }
        if (queryDTO.getWithAttr()) {
            List<Long> groupIds = records.stream().map(AttrGroupRespDTO::getId).collect(Collectors.toList());
            Map<Long, List<AttrDO>> attrMap = listByGroupIds(groupIds);
            if (MapUtils.isNotEmpty(attrMap)) {
                for (AttrGroupRespDTO record : records) {
                    List<AttrDO> attrList = attrMap.get(record.getId());
                    record.setAttrList(BeanConvertor.copyList(attrList, AttrRespDTO.class));
                }
            }
        }
        return PageResponse.of(page);
    }

    public Long updateAttrGroup(AttrGroupUpdateReqDTO reqDTO) {
        attrTemplateService.checkTemplateExists(reqDTO.getAttrTemplateId());
        AttrGroupDO entity = BeanConvertor.copy(reqDTO, AttrGroupDO.class);
        attrGroupService.updateById(entity);
        return entity.getId();
    }

    public AttrGroupRespDTO getAttrGroupInfo(Long AttrGroupId) {
        AttrGroupDO entity = attrGroupService.getById(AttrGroupId);
        return BeanConvertor.copy(entity, AttrGroupRespDTO.class);
    }

    public void saveAttrGroupRel(Long attrGroupId, Long attrId) {
        AttrGroupRelDO entity = new AttrGroupRelDO();
        entity.setAttrId(attrId);
        entity.setAttrGroupId(attrGroupId);
        attrGroupService.saveAttrGroupRel(attrGroupId, attrId);
    }

    public Long countById(Long attrGroupId) {
        if (attrGroupId == null) {
            return 0L;
        }
        return lambdaQuery().eq(BaseEntity::getId, attrGroupId).count();
    }
    public void removeByAttrId(Long id) {
        lambdaUpdate()
                .eq(BaseEntity::getId, id)
                .remove();
    }
}
