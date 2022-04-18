package com.kt.cloud.commodity.module.attr.support;

import com.google.common.collect.Lists;
import com.kt.cloud.commodity.dao.entity.AttrValueDO;
import com.kt.cloud.commodity.module.attr.dto.response.AttrRespDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.response.AttrValueRespDTO;
import com.kt.component.web.util.bean.BeanConvertor;
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
     * @param attrValueDOList
     */
    public void fillAttrValues(List<AttrRespDTO> records, List<AttrValueDO> attrValueDOList) {
        if (CollectionUtils.isNotEmpty(attrValueDOList)) {
            Map<Long, List<AttrValueDO>> attrValueMap = attrValueDOList.stream()
                    .collect(Collectors.groupingBy(AttrValueDO::getAttrId));
            for (AttrRespDTO record : records) {
                List<AttrValueDO> valueList = attrValueMap.get(record.getId());
                if (CollectionUtils.isNotEmpty(valueList)) {
                    record.setValues(BeanConvertor.copyList(valueList, AttrValueRespDTO.class));
                }
            }
        }
    }

    public void fillAttrValues(AttrRespDTO record, List<AttrValueDO> attrValueDOList) {
        fillAttrValues(Lists.newArrayList(record), attrValueDOList);
    }

}
