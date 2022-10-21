package com.ark.center.commodity.domain.attr.assembler;


import com.ark.center.commodity.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrTemplateUpdateCmd;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.domain.attr.aggregate.AttrTemplate;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttrTemplateAssembler {

    public PageResponse<AttrTemplateDTO> entityToDTO(PageResponse<AttrTemplate> page) {
        return page.convert(item -> BeanConvertor.copy(item, AttrTemplateDTO.class));
    }

    public PageResponse<AttrTemplateDTO> entityToDTO(IPage<AttrTemplate> page) {
        return BeanConvertor.copyPage(page, AttrTemplateDTO.class);
    }

    public List<AttrTemplateDTO> entityToDTO(List<AttrTemplate> list) {
        return BeanConvertor.copyList(list, AttrTemplateDTO.class);
    }

    public PageResponse<AttrTemplateDTO> cmdToEntity(PageResponse<AttrTemplate> page) {
        return BeanConvertor.copyPage(page, AttrTemplateDTO.class);
    }

    public AttrTemplate updateCmdToAggregate(AttrTemplateUpdateCmd command) {
        return BeanConvertor.copy(command, AttrTemplate.class);
    }

    public AttrTemplate createCmdToAggregate(AttrTemplateCreateCmd command) {
        return BeanConvertor.copy(command, AttrTemplate.class);
    }

    public AttrTemplateDTO entityToDTO(AttrTemplate category) {
        return BeanConvertor.copy(category, AttrTemplateDTO.class);
    }
}














