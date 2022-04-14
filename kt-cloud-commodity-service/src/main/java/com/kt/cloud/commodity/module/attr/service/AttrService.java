package com.kt.cloud.commodity.module.attr.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.dao.entity.AttrDO;
import com.kt.cloud.commodity.dao.entity.AttrValueDO;
import com.kt.cloud.commodity.dao.mapper.AttrMapper;
import com.kt.cloud.commodity.module.attr.dto.request.AttrPageQueryReqDTO;
import com.kt.cloud.commodity.module.attr.dto.request.AttrUpdateReqDTO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.cloud.commodity.module.attrvalue.service.AttrValueService;
import com.kt.component.common.ParamsChecker;
import com.kt.component.dto.PageResponse;
import com.kt.component.exception.ExceptionFactory;
import com.kt.component.web.util.bean.BeanConvertor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final AttrValueService attrValueService;
    private final AttrTemplateService attrTemplateService;

    public AttrService(AttrGroupService attrGroupService,
                       AttrValueService attrValueService,
                       AttrTemplateService attrTemplateService) {
        this.attrGroupService = attrGroupService;
        this.attrValueService = attrValueService;
        this.attrTemplateService = attrTemplateService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long createAttr(AttrUpdateReqDTO reqDTO) {
        doCheck(reqDTO);

        AttrDO attrDO = BeanConvertor.copy(reqDTO, AttrDO.class);
        // 保存属性基本信息
        save(attrDO);
        // 保存属性值
        saveValues(attrDO, reqDTO.getValues());
        return attrDO.getId();
    }

    public PageResponse<AttrRespDTO> getPageList(AttrPageQueryReqDTO queryDTO) {
        IPage<AttrRespDTO> page = lambdaQuery()
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrDO::getName, queryDTO.getName())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrRespDTO.class));
        return BeanConvertor.copyPage(page, AttrRespDTO.class);
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
        count = attrGroupService.countById(reqDTO.getAttrGroupId());
        if (count.equals(0L)) {
            throw ExceptionFactory.userException("属性组不存在");
        }
        Long attrId = reqDTO.getId();
        if (attrId != null && attrId > 0L) {
            AttrDO attrOldDO = getById(attrId);
            ParamsChecker.throwIfIsNull(attrOldDO, ExceptionFactory.userException("属性不存在"));
        }
    }

    private void attemptRemoveAttrValues(Long attrId, AttrDO attrNewDO) {
        // 如果录入方式为[SELECT]，把attr_value先清掉
        if ((attrNewDO.getInputType().equals(AttrDO.InputType.SELECT.getValue()))) {
            attrValueService.removeByAttrId(attrId);
        }
    }

    private void saveValues(AttrDO attrDO, List<String> values) {
        if (attrDO.getInputType().equals(AttrDO.InputType.SELECT.getValue())) {
            ParamsChecker.throwIfIsEmpty(values, ExceptionFactory.userException("属性值选项不能为空"));
            attrValueService.batchSave(attrDO.getId(), values, AttrValueDO.Type.COMMON.getValue());
        }
    }

    public AttrRespDTO getAttrInfo(Long attrId) {
        AttrDO entity = getById(attrId);
        AttrRespDTO respDTO = BeanConvertor.copy(entity, AttrRespDTO.class);
        List<AttrValueDO> attrValueDOList = attrValueService.listByAttrId(attrId);
        if (CollUtil.isNotEmpty(attrValueDOList)) {
            respDTO.setValues(attrValueDOList.stream().map(AttrValueDO::getValue).collect(Collectors.toList()));
        }
        return respDTO;
    }

}
