package com.ark.center.commodity.domain.attr.factory;

import com.ark.center.commodity.client.attr.command.AttrSaveCmd;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.domain.attr.assembler.AttrAssembler;
import com.ark.center.commodity.domain.attr.repository.AttrGroupRepository;
import com.ark.center.commodity.domain.attr.repository.AttrRepository;
import com.ark.center.commodity.domain.attr.repository.AttrTemplateRepository;
import com.ark.center.commodity.domain.attr.vo.AttrOption;
import com.ark.component.common.ParamsChecker;
import com.ark.component.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AttrFactory {

    private final AttrTemplateRepository attrTemplateRepository;
    private final AttrGroupRepository attrGroupRepository;
    private final AttrRepository attrRepository;

    private final AttrAssembler attrAssembler;

    public Attr create(AttrSaveCmd cmd) {
        doCheck(cmd);
        Attr aggregate = attrAssembler.createCmdToAggregate(cmd);
        fillOptions(aggregate, cmd.getValues());
        return aggregate;
    }

    private void fillOptions(Attr aggregate, List<String> values) {
        if (CollectionUtils.isNotEmpty(values)) {
            List<AttrOption> optionList = new ArrayList<>(values.size());
            for (String value : values) {
                AttrOption valueDO = new AttrOption(value, AttrOption.Type.COMMON);
//                if (spuId != null && spuId > 0) {
//                    valueDO.setSpuId(spuId);
//                }
                optionList.add(valueDO);
            }
            aggregate.setOptions(optionList);
        }
    }


    private void doCheck(AttrSaveCmd cmd) {
        Long attrTemplateId = cmd.getAttrTemplateId();
        Long count = attrTemplateRepository.countById(attrTemplateId);
        if (count.equals(0L)) {
            throw ExceptionFactory.userException("属性模板不存在");
        }
        Long attrGroupId = cmd.getAttrGroupId();
        if (attrGroupId != null && attrGroupId > 0) {
            count = attrGroupRepository.countById(attrGroupId);
            if (count.equals(0L)) {
                throw ExceptionFactory.userException("属性组不存在");
            }
        }
        Long attrId = cmd.getId();
        if (attrId != null && attrId > 0L) {
            Attr attrOldDO = attrRepository.findById(attrId);
            ParamsChecker.throwIfIsNull(attrOldDO, ExceptionFactory.userException("属性不存在"));
        }
    }
}
