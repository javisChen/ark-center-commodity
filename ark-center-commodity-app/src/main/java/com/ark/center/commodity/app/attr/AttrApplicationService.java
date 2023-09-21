package com.ark.center.commodity.app.attr;


import cn.hutool.core.collection.CollUtil;
import com.ark.center.commodity.app.attr.executor.AttrCreateCmdExe;
import com.ark.center.commodity.client.attr.command.AttrCreateCmd;
import com.ark.center.commodity.client.attr.command.AttrGroupCmd;
import com.ark.center.commodity.client.attr.command.AttrTemplateCreateCmd;
import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.dto.AttrGroupDTO;
import com.ark.center.commodity.client.attr.dto.AttrTemplateDTO;
import com.ark.center.commodity.client.attr.query.AttrGroupPageQry;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.client.attr.query.AttrTemplatePageQry;
import com.ark.center.commodity.domain.attr.AttrGroup;
import com.ark.center.commodity.domain.attr.AttrTemplate;
import com.ark.center.commodity.domain.attr.repository.AttrGateway;
import com.ark.center.commodity.domain.attr.repository.AttrGroupGateway;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrGroupConverter;
import com.ark.center.commodity.infra.attr.convertor.AttrTemplateConverter;
import com.ark.component.common.ParamsChecker;
import com.ark.component.common.util.assemble.AssembleHelper;
import com.ark.component.dto.PageResponse;
import com.ark.component.exception.ExceptionFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
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
    private final AttrGroupConverter attrGroupConverter;

    private final AttrTemplateConverter attrTemplateConverter;
    private final AttrTemplateGateway attrTemplateGateway;

    private final AttrGateway attrGateway;
    private final AttrCreateCmdExe attrCreateCmdExe;

    public Long save(AttrTemplateCreateCmd cmd) {
        AttrTemplate attrTemplate = attrTemplateConverter.toAttrTemplate(cmd);
        if (attrTemplate.getId() != null) {
            attrTemplateGateway.update(attrTemplate);
            return attrTemplate.getId();
        }
        return attrTemplateGateway.insert(attrTemplate);
    }

    public PageResponse<AttrTemplateDTO> queryPages(AttrTemplatePageQry queryDTO) {
        IPage<AttrTemplateDTO> pages = attrTemplateGateway.selectPages(queryDTO);
        return PageResponse.of(pages);
    }


    public AttrTemplateDTO queryDetails(Long attrTemplateId) {
        AttrTemplate attrTemplate = attrTemplateGateway.selectById(attrTemplateId);
        return attrTemplateConverter.toDTO(attrTemplate);
    }

    public Long saveAttrGroup(AttrGroupCmd cmd) {
        AttrTemplate attrTemplate = attrTemplateGateway.selectById(cmd.getAttrTemplateId());
        ParamsChecker.throwIfIsNull(attrTemplate, ExceptionFactory.userException("属性模板不存在"));
        com.ark.center.commodity.domain.attr.AttrGroup attrGroup = attrGroupConverter.toAttrGroup(cmd);
        if (attrGroup.getId() != null) {
            attrGroupGateway.update(attrGroup);
        } else {
            attrGroupGateway.insert(attrGroup);
        }
        return attrGroup.getId();
    }

    public PageResponse<AttrGroupDTO> queryGroupPages(AttrGroupPageQry queryDTO) {
        IPage<AttrGroupDTO> pages = attrGroupGateway.selectPages(queryDTO);
        List<AttrGroupDTO> records = pages.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(pages);
        }
        if (queryDTO.getWithAttr()) {
            AssembleHelper.newBuilder(records)
                            .build()
                            .execute();
            fill(records,
                    AttrGroupDTO::getId,
                    attrGateway::selectByGroupIds,
                    AttrDTO::getAttrGroupId,
                    AttrGroupDTO::setAttrList);
        }
        return PageResponse.of(pages);
    }

    public static <SOURCE, TARGET> void fill(List<SOURCE> records,
                                             Function<? super SOURCE, Long> sourceIdFunc,
                                             Function<List<Long>, List<TARGET>> targetQueryFunc,
                                             Function<? super TARGET, Long> targetIdFunc,
                                             BiConsumer<SOURCE, List<TARGET>> sourceConsumer) {
        List<Long> groupIds = CollUtil.map(records, sourceIdFunc, true);
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        List<TARGET> targets = targetQueryFunc.apply(groupIds);
        if (CollectionUtils.isEmpty(targets)) {
            return;
        }
        Map<Long, List<TARGET>> map = targets.stream().collect(Collectors.groupingBy(targetIdFunc));
        if (MapUtils.isEmpty(map)) {
            return;
        }
        records.forEach(record -> {
            List<TARGET> targetList = map.get(sourceIdFunc.apply(record));
            sourceConsumer.accept(record, targetList);
        });
    }

    public AttrGroupDTO queryAttrGroupDetails(Long id) {
        AttrGroup aggregate = attrGroupGateway.selectById(id);
        return attrGroupConverter.toDTO(aggregate);
    }

    public Long createAttr(AttrCreateCmd cmd) {
        return attrCreateCmdExe.execute(cmd);
    }

    public Long updateAttr(AttrCreateCmd cmd) {
        return attrCreateCmdExe.execute(cmd);
    }

    public void removeByAttrId(Long id) {
        attrGateway.remove(id);
    }

    public AttrDTO getDetails(Long id) {
        return attrGateway.selectById(id);
    }

    public PageResponse<AttrDTO> queryPages(AttrPageQry queryDTO) {
        IPage<AttrDTO> page = attrGateway.selectPages(queryDTO);
        return PageResponse.of(page);
    }
}
