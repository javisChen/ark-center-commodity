package com.ark.center.commodity.application.attr.service;


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
import com.ark.center.commodity.domain.attr.repository.AttrGroupRepository;
import com.ark.center.commodity.domain.attr.repository.AttrRepository;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateRepository;
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

    private final AttrGroupRepository attrGroupRepository;
    private final AttrGroupAssembler attrGroupAssembler;
    private final AttrTemplateAssembler attrTemplateAssembler;
    private final AttrTemplateRepository attrTemplateRepository;
    private final AttrAssembler attrAssembler;
    private final AttrRepository attrRepository;
    private final AttrFactory attrFactory;

    public Long createAttrTemplate(AttrTemplateCreateCmd reqDTO) {
        AttrTemplate aggregate = attrTemplateAssembler.createCmdToAggregate(reqDTO);
        return attrTemplateRepository.store(aggregate);
    }

    public PageResponse<AttrTemplateDTO> getAttrTemplatePageList(AttrTemplatePageQry queryDTO) {
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
        AttrGroup aggregate = attrGroupAssembler.updateCmdToAggregate(reqDTO);
        attrGroupRepository.update(aggregate);
    }

    public PageResponse<AttrGroupDTO> getAttrGroupPageList(AttrGroupPageQry queryDTO) {
        IPage<AttrGroup> pageList = attrGroupRepository.pageList(queryDTO);
        return attrGroupAssembler.toDTO(pageList);
    }

    public AttrGroupDTO getAttrGroupInfo(Long id) {
        AttrGroup aggregate = attrGroupRepository.findById(id);
        return attrGroupAssembler.toDTO(aggregate);
    }

    public Long createAttr(AttrSaveCmd cmd) {
        if (cmd.getId() != null) {
            updateAttr(cmd);
            return 0L;
        }
        Attr attr = attrFactory.create(cmd);
        // 如果录入方式为[SELECT]，把attr_value先清掉
        if (attr.isSelectInputType()) {
            attr.removeOptions();
        }
        return attrRepository.store(attr);
    }

    public void updateAttr(AttrSaveCmd reqDTO) {
    }

    public void removeByAttrId(Long id) {
        attrRepository.remove(id);
    }

    public AttrDTO getAttrInfo(Long id) {
        return null;
    }

    public PageResponse<AttrDTO> getAttrPageList(AttrPageQry queryDTO) {
        IPage<Attr> page = attrRepository.pageList(queryDTO);
        return attrAssembler.toDTO(page);
    }
}
