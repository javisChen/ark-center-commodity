package com.ark.center.product.infra.product.repository.es.doc;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class AttrOptionDoc {

    @Field(type = FieldType.Keyword, index = false)
    private String value;

}
