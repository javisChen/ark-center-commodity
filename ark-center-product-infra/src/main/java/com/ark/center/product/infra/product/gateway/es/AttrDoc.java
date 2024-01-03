package com.ark.center.product.infra.product.gateway.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class AttrDoc {

    @Field(type = FieldType.Long)
    private Long attrId;

    @Field(index = false, type = FieldType.Keyword, docValues = false)
    private String attrName;

    @Field(type = FieldType.Keyword)
    private String attrValue;
}
