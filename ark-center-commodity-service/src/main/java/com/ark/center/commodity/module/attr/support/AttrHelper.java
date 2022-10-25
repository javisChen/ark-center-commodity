package com.ark.center.commodity.module.attr.support;

import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionDO;
import com.ark.center.commodity.module.attr.dto.response.AttrOptionRespDTO;
import com.ark.center.commodity.module.attr.dto.response.AttrRespDTO;
import com.ark.component.web.util.bean.BeanConvertor;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AttrHelper {

    /**
     * 填充属性值
     * @param records
     * @param attrOptionDOList
     */
    public void fillAttrValues(List<AttrRespDTO> records, List<AttrOptionDO> attrOptionDOList) {
        if (CollectionUtils.isNotEmpty(attrOptionDOList)) {
            Map<Long, List<AttrOptionDO>> attrValueMap = attrOptionDOList.stream()
                    .collect(Collectors.groupingBy(AttrOptionDO::getAttrId));
            for (AttrRespDTO record : records) {
                List<AttrOptionDO> valueList = attrValueMap.get(record.getId());
                if (CollectionUtils.isNotEmpty(valueList)) {
                    record.setOptionList(BeanConvertor.copyList(valueList, AttrOptionRespDTO.class));
                }
            }
        }
    }

    public void fillAttrValues(AttrRespDTO record, List<AttrOptionDO> attrOptionDOList) {
        fillAttrValues(Lists.newArrayList(record), attrOptionDOList);
    }

}
