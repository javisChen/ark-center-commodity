//package com.ark.center.commodity.domain.attr.factory;
//
//import com.ark.center.commodity.client.attr.command.AttrCreateCmd;
//import com.ark.center.commodity.domain.attr.aggregate.Attr;
//import com.ark.center.commodity.domain.attr.assembler.AttrAssembler;
//import com.ark.center.commodity.domain.attr.repository.AttrGroupGateway;
//import com.ark.center.commodity.domain.attr.repository.AttrGateway;
//import com.ark.center.commodity.domain.attr.repository.AttrTemplateGateway;
//import com.ark.center.commodity.domain.attr.vo.AttrOption;
//import com.ark.component.common.ParamsChecker;
//import com.ark.component.exception.ExceptionFactory;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class AttrFactory {
//
//    private final AttrTemplateGateway attrTemplateGateway;
//    private final AttrGroupGateway attrGroupGateway;
//    private final AttrGateway attrGateway;
//
//    private final AttrAssembler attrAssembler;
//
//    public Attr create(AttrCreateCmd cmd) {
//        doCheck(cmd);
//        Attr aggregate = attrAssembler.createCmdToAggregate(cmd);
//        fillOptions(aggregate, cmd.getValues());
//        return aggregate;
//    }
//
//    private void fillOptions(Attr aggregate, List<String> values) {
//        if (CollectionUtils.isNotEmpty(values)) {
//            List<AttrOption> optionList = new ArrayList<>(values.size());
//            for (String value : values) {
//                AttrOption valueDO = new AttrOption(value, AttrOption.Type.COMMON);
////                if (spuId != null && spuId > 0) {
////                    valueDO.setSpuId(spuId);
////                }
//                optionList.add(valueDO);
//            }
//            aggregate.setOptions(optionList);
//        }
//    }
//
//
//    private void doCheck(AttrCreateCmd cmd) {
//        Long attrTemplateId = cmd.getAttrTemplateId();
//        Long count = attrTemplateGateway.countById(attrTemplateId);
//        if (count.equals(0L)) {
//            throw ExceptionFactory.userException("属性模板不存在");
//        }
//        Long attrGroupId = cmd.getAttrGroupId();
//        if (attrGroupId != null && attrGroupId > 0) {
//            count = attrGroupGateway.countById(attrGroupId);
//            if (count.equals(0L)) {
//                throw ExceptionFactory.userException("属性组不存在");
//            }
//        }
//        Long attrId = cmd.getId();
//        if (attrId != null && attrId > 0L) {
//            Attr attrOldDO = attrGateway.selectById(attrId);
//            ParamsChecker.throwIfIsNull(attrOldDO, ExceptionFactory.userException("属性不存在"));
//        }
//    }
//}
