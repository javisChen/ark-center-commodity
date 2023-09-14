package com.ark.center.commodity.app.attr;


import cn.hutool.core.collection.CollUtil;
import com.ark.center.commodity.app.attr.executor.AttrCreateCmdExe;
import com.ark.center.commodity.client.attr.command.*;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.domain.attr.aggregate.AttrGroup;
import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.center.commodity.domain.attr.assembler.AttrAssembler;
import com.ark.center.commodity.domain.attr.assembler.AttrGroupAssembler;
import com.ark.center.commodity.domain.attr.assembler.AttrTemplateAssembler;
import com.ark.center.commodity.domain.attr.factory.AttrFactory;
import com.ark.center.commodity.domain.attr.repository.AttrGateway;
import com.ark.center.commodity.domain.attr.repository.AttrGroupGateway;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateGateway;
import com.ark.component.common.ParamsChecker;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性模板 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
@RequiredArgsConstructor
public class AttrApplicationService {

    private final AttrGroupGateway attrGroupGateway;
    private final AttrGroupAssembler attrGroupAssembler;
    private final AttrTemplateAssembler attrTemplateAssembler;
    private final AttrTemplateGateway attrTemplateGateway;
    private final AttrAssembler attrAssembler;
    private final AttrGateway attrGateway;
    private final AttrFactory attrFactory;
    private final AttrCreateCmdExe attrCreateCmdExe;

    public Long createAttrTemplate(AttrTemplateCreateCmd reqDTO) {
        AttrTemplate aggregate = attrTemplateAssembler.createCmdToAggregate(reqDTO);
        return attrTemplateGateway.store(aggregate);
    }

    public PageResponse<AttrTemplateDTO> getAttrTemplatePageList(AttrTemplatePageQry queryDTO) {
        IPage<AttrTemplate> page = attrTemplateGateway.selectPages(queryDTO);
        return BeanConvertor.copyPage(page, AttrTemplateDTO.class);
    }

    public void updateAttrTemplate(AttrTemplateUpdateCmd reqDTO) {
        AttrTemplate aggregate = attrTemplateAssembler.updateCmdToAggregate(reqDTO);
        attrTemplateGateway.update(aggregate);
    }

    public AttrTemplateDTO getAttrTemplateInfo(Long attrTemplateId) {
        AttrTemplate aggregate = attrTemplateGateway.selectById(attrTemplateId);
        return attrTemplateAssembler.toDTO(aggregate);
    }

    public void checkTemplateExists(Long attrTemplateId) {
        AttrTemplate aggregate = attrTemplateGateway.selectById(attrTemplateId);
        ParamsChecker.throwIfIsNull(aggregate, ExceptionFactory.userException("属性模板不存在"));
    }

    public Long countById(Long attrTemplateId) {
        return attrTemplateGateway.countById(attrTemplateId);
    }

    public Long createAttrGroup(AttrGroupCreateCmd reqDTO) {
        AttrTemplate attrTemplate = attrTemplateGateway.selectById(reqDTO.getAttrTemplateId());
        ParamsChecker.throwIfIsNull(attrTemplate, ExceptionFactory.userException("属性模板不存在"));

        AttrGroup aggregate = attrGroupAssembler.createCmdToAggregate(reqDTO);
        attrGroupGateway.store(aggregate);
        return aggregate.getId();
    }

    public void updateAttrGroup(AttrGroupUpdateCmd reqDTO) {
        AttrGroup aggregate = attrGroupAssembler.updateCmdToAggregate(reqDTO);
        attrGroupGateway.update(aggregate);
    }

    public PageResponse<AttrGroupDTO> getAttrGroupPageList(AttrGroupPageQry queryDTO) {
        IPage<AttrGroup> page = attrGroupGateway.selectPages(queryDTO);
        List<AttrGroup> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return attrGroupAssembler.toDTO(page);
        }
        if (queryDTO.getWithAttr()) {
            List<Long> groupIds = CollUtil.map(records, AttrGroup::getId, true);
            List<com.ark.center.commodity.domain.attr.Attr> attrList = attrGateway.selectByGroupIds(groupIds);
            Map<Long, List<Attr>> attrMap = attrList.stream().collect(Collectors.groupingBy(Attr::getAttrGroupId));
            if (MapUtils.isNotEmpty(attrMap)) {
                records.forEach(record -> record.setAttrList(attrMap.get(record.getId())));
            }
        }
        return attrGroupAssembler.toDTO(page);
    }

    public AttrGroupDTO getAttrGroupInfo(Long id) {
        AttrGroup aggregate = attrGroupGateway.selectById(id);
        return attrGroupAssembler.toDTO(aggregate);
    }

    public Long createAttr(AttrCreateCmd cmd) {
        return attrCreateCmdExe.execute(cmd);
        Attr attr = attrFactory.create(cmd);
        // 如果录入方式为[SELECT]，把attr_value先清掉
        if (attr.isSelectInputType()) {
            attr.removeOptions();
        }
        return attrGateway.store(attr);
    }

    public void updateAttr(AttrCreateCmd reqDTO) {
        Attr attr = attrFactory.create(reqDTO);
        attrGateway.store(attr);
    }

    public void removeByAttrId(Long id) {
        attrGateway.remove(id);
    }

    public AttrDTO getDetails(Long id) {
        com.ark.center.commodity.domain.attr.Attr attr = attrGateway.selectById(id);
        return attrAssembler.toDTO(attr);
    }

    public PageResponse<AttrDTO> getAttrPageList(AttrPageQry queryDTO) {
        IPage<Attr> page = attrGateway.selectPages(queryDTO);

        return attrAssembler.toDTO(page);
    }
}
