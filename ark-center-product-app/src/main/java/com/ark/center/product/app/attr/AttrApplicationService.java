package com.ark.center.product.app.attr;


import cn.hutool.core.lang.Assert;
import com.ark.center.product.app.attr.executor.AttrCreateCmdExe;
import com.ark.center.product.client.attr.command.AttrCreateCmd;
import com.ark.center.product.client.attr.command.AttrGroupCmd;
import com.ark.center.product.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.product.client.attr.dto.AttrDTO;
import com.ark.center.product.client.attr.dto.AttrGroupDTO;
import com.ark.center.product.client.attr.dto.AttrTemplateDTO;
import com.ark.center.product.client.attr.query.AttrGroupPageQry;
import com.ark.center.product.client.attr.query.AttrPageQry;
import com.ark.center.product.client.attr.query.AttrTemplatePageQry;
import com.ark.center.product.infra.attr.AttrGroup;
import com.ark.center.product.infra.attr.AttrTemplate;
import com.ark.center.product.infra.attr.convertor.AttrGroupConverter;
import com.ark.center.product.infra.attr.convertor.AttrTemplateConverter;
import com.ark.center.product.infra.attr.gateway.AttrGroupGateway;
import com.ark.center.product.infra.attr.gateway.AttrTemplateGateway;
import com.ark.center.product.infra.attr.gateway.impl.AttrService;
import com.ark.center.product.infra.category.Category;
import com.ark.center.product.infra.category.service.CategoryService;
import com.ark.component.common.util.assemble.DataProcessor;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final CategoryService categoryService;
    private final AttrGroupConverter attrGroupConverter;

    private final AttrTemplateConverter attrTemplateConverter;
    private final AttrTemplateGateway attrTemplateGateway;

    private final AttrService attrService;
    private final AttrCreateCmdExe attrCreateCmdExe;

    public Long saveAttrTemplate(AttrTemplateCreateCmd cmd) {
        AttrTemplate attrTemplate = attrTemplateConverter.toAttrTemplate(cmd);
        if (attrTemplate.getId() != null) {
            attrTemplateGateway.update(attrTemplate);
            return attrTemplate.getId();
        }
        return attrTemplateGateway.insert(attrTemplate);
    }

    public PageResponse<AttrTemplateDTO> queryAttrTemplatePages(AttrTemplatePageQry queryDTO) {
        IPage<AttrTemplateDTO> pages = attrTemplateGateway.selectPages(queryDTO);
        return PageResponse.of(pages);
    }


    public AttrTemplateDTO queryAttrTemplateDetails(Long attrTemplateId) {
        AttrTemplate attrTemplate = attrTemplateGateway.selectById(attrTemplateId);
        return attrTemplateConverter.toDTO(attrTemplate);
    }

    public Long saveAttrGroup(AttrGroupCmd cmd) {
        AttrTemplate attrTemplate = attrTemplateGateway.selectById(cmd.getAttrTemplateId());
        Assert.notNull(attrTemplate, ExceptionFactory.userExceptionSupplier("属性模板不存在"));
        AttrGroup attrGroup = attrGroupConverter.toAttrGroup(cmd);
        if (attrGroup.getId() != null) {
            attrGroupGateway.update(attrGroup);
        } else {
            attrGroupGateway.insert(attrGroup);
        }
        return attrGroup.getId();
    }

    public PageResponse<AttrGroupDTO> queryGroupPages(AttrGroupPageQry queryDTO) {
        Long categoryId = queryDTO.getCategoryId();
        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            queryDTO.setAttrTemplateId(category.getAttrTemplateId());
        }
        IPage<AttrGroupDTO> pages = attrGroupGateway.selectPages(queryDTO);
        List<AttrGroupDTO> records = pages.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(pages);
        }
        if (queryDTO.getWithAttr()) {
            DataProcessor.create(records)
                    .keySelect(AttrGroupDTO::getId)
                    .query(attrService::selectByGroupIds)
                    .keyBy(AttrDTO::getAttrGroupId)
                    .collection()
                    .process(AttrGroupDTO::setAttrList);
        }
        return PageResponse.of(pages);
    }

    public AttrGroupDTO queryAttrGroupDetails(Long id) {
        AttrGroup aggregate = attrGroupGateway.selectById(id);
        return attrGroupConverter.toDTO(aggregate);
    }

    public Long saveAttr(AttrCreateCmd cmd) {
        return attrCreateCmdExe.execute(cmd);
    }

    public Long updateAttr(AttrCreateCmd cmd) {
        return attrCreateCmdExe.execute(cmd);
    }

    public void removeByAttrId(Long id) {
        attrService.remove(id);
    }

    public AttrDTO queryAttrDetails(Long id) {
        return attrService.selectById(id);
    }

    public PageResponse<AttrDTO> queryAttrTemplatePages(AttrPageQry queryDTO) {
        IPage<AttrDTO> page = attrService.selectPages(queryDTO);
        return PageResponse.of(page);
    }
}
