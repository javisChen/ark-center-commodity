package com.ark.center.product.app.attr.executor;

import com.ark.center.product.client.attr.command.AttrCreateCmd;
import com.ark.center.product.domain.attr.Attr;
import com.ark.center.product.domain.attr.AttrOption;
import com.ark.center.product.domain.attr.assembler.AttrAssembler;
import com.ark.center.product.domain.attr.repository.AttrGateway;
import com.ark.center.product.domain.attr.repository.AttrGroupGateway;
import com.ark.center.product.domain.attr.repository.AttrTemplateGateway;
import com.ark.component.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AttrCreateCmdExe {

    private final AttrTemplateGateway attrTemplateGateway;
    private final AttrGroupGateway attrGroupGateway;
    private final AttrGateway attrGateway;
    private final AttrAssembler attrAssembler;

    public Long execute(AttrCreateCmd cmd) {

        preCheck(cmd);

        Attr attr = attrAssembler.toAttr(cmd);

        saveAttr(attr);

        saveOptions(attr, cmd);

        return attr.getId();
    }

    private void saveAttr(Attr attr) {
        if (attr.getId() != null) {
            attrGateway.update(attr);
        } else {
            attrGateway.insert(attr);
        }
    }

    private void saveOptions(Attr attr, AttrCreateCmd cmd) {
        List<AttrOption> options = assembleOptions(attr, cmd.getValues());

        attrGateway.deleteOptions(attr.getId());

        if (attr.isSelectInputType()) {
            options.forEach(item -> item.setAttrId(attr.getId()));
            attrGateway.saveOptions(options);
        }
    }

    private List<AttrOption> assembleOptions(Attr attr, List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }
        List<AttrOption> optionList = new ArrayList<>(values.size());
        for (String value : values) {
            AttrOption valueDO = new AttrOption();
            valueDO.setAttrId(attr.getId());
            valueDO.setValue(value);
            valueDO.setType(AttrOption.Type.COMMON.getValue());
            optionList.add(valueDO);
        }
        return optionList;
    }


    private void preCheck(AttrCreateCmd cmd) {
        Long attrTemplateId = cmd.getAttrTemplateId();
        Long count = attrTemplateGateway.countById(attrTemplateId);
        if (count.equals(0L)) {
            throw ExceptionFactory.userException("属性模板不存在");
        }
        Long attrGroupId = cmd.getAttrGroupId();
        if (attrGroupId != null && attrGroupId > 0) {
            count = attrGroupGateway.countById(attrGroupId);
            if (count.equals(0L)) {
                throw ExceptionFactory.userException("属性组不存在");
            }
        }
    }


}
