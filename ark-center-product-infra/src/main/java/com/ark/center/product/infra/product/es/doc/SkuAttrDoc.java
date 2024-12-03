package com.ark.center.product.infra.product.es.doc;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
public class SkuAttrDoc {

    @Field(type = FieldType.Long)
    private Long attrId;

    @Field(type = FieldType.Keyword, index = false)
    private String attrName;

    @Field(type = FieldType.Keyword)
    private String attrValue;

}
