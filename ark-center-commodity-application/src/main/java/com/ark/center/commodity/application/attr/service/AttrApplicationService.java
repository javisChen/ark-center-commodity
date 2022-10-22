package com.ark.center.commodity.application.attr.service;


import com.ark.center.commodity.client.attr.command.AttrGroupCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrGroupUpdateCmd;
import com.ark.center.commodity.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrTemplateUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
import com.ark.center.commodity.domain.attr.aggregate.AttrGroup;
import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.center.commodity.domain.attr.assembler.AttrGroupAssembler;
import com.ark.center.commodity.domain.attr.assembler.AttrTemplateAssembler;
import com.ark.center.commodity.domain.attr.repository.AttrGroupRepository;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateRepository;
import com.ark.center.commodity.infrastructure.db.dataobject.AttrGroupDO;
import com.ark.component.common.ParamsChecker;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final AttrTemplateRepository attrTemplateRepository;

    private final AttrGroupRepository attrGroupRepository;
    private final AttrTemplateAssembler attrTemplateAssembler;

    private final AttrGroupAssembler attrGroupAssembler;

    public Long createAttrTemplate(AttrTemplateCreateCmd reqDTO) {
        AttrTemplate aggregate = attrTemplateAssembler.createCmdToAggregate(reqDTO);
        return attrTemplateRepository.store(aggregate);
    }

    public PageResponse<AttrTemplateDTO> getPageList(AttrTemplatePageQry queryDTO) {
        IPage<AttrTemplate> page = attrTemplateRepository.pageList(queryDTO);
        return BeanConvertor.copyPage(page, AttrTemplateDTO.class);
    }

    public boolean updateAttrTemplate(AttrTemplateUpdateCmd reqDTO) {
        AttrTemplate aggregate = attrTemplateAssembler.updateCmdToAggregate(reqDTO);
        return attrTemplateRepository.update(aggregate);
    }

    public AttrTemplateDTO getAttrTemplateInfo(Long attrTemplateId) {
        AttrTemplate aggregate = attrTemplateRepository.findById(attrTemplateId);
        return attrTemplateAssembler.toDTO(aggregate);
    }

    public void checkTemplateExists(Long attrTemplateId) {
        AttrTemplate aggregate = attrTemplateRepository.findById(attrTemplateId);
        ParamsChecker.throwIfIsNull(aggregate, ExceptionFactory.userException("属性模板不存在"));
    }

    public Long countById(Long attrTemplateId) {
        return attrTemplateRepository.countById(attrTemplateId);
    }

    public Long createAttrGroup(AttrGroupCreateCmd reqDTO) {
        AttrTemplate attrTemplate = attrTemplateRepository.findById(reqDTO.getAttrTemplateId());
        ParamsChecker.throwIfIsNull(attrTemplate, ExceptionFactory.userException("属性模板不存在"));

        AttrGroup aggregate = attrGroupAssembler.createCmdToAggregate(reqDTO);
        attrGroupRepository.store(aggregate);
        return aggregate.getId();
    }

    public void updateAttrGroup(AttrGroupUpdateCmd reqDTO) {

    }

    public PageResponse<AttrGroupDTO> getAttrGroupPageList(AttrGroupPageQry queryDTO) {
    }

    public AttrGroupDTO getAttrGroupInfo(Long id) {
        return null;
    }
}
