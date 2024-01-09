package com.ark.center.product.infra.product.gateway.es;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * SKU ES索引
 */
@Document(indexName = "sku")
@Data
public class SkuDoc {

    @Id
    @Field(type = FieldType.Long)
    private Long skuId;

    @Field(type = FieldType.Keyword)
    private Long spuId;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String skuName;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String brandName;

    @Field(type = FieldType.Keyword, index = false, docValues = false)
    private String categoryName;

    @Field(type = FieldType.Long)
    private Long brandId;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Keyword)
    private Integer showPrice;

    @Field()
    private List<String> pictures;

    /**
     * 使用ZonedDateTime，序列化的时间格式会带上时区，例如2023-09-27T17:23:54.000+08:00。这样ES会自动根据时区来识别时间
     */
    @Field(index = false, type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime createTime;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime updateTime;

    @Field(type = FieldType.Nested)
    private List<AttrDoc> attrs;

}
