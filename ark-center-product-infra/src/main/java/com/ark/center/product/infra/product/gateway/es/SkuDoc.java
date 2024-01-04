package com.ark.center.product.infra.product.gateway.es;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SKU ES索引
 */
@Document(indexName = "sku")
@Data
public class SkuDoc {

    @Id
    @Field(
            type = FieldType.Long)
    private Long skuId;

    @Field(
            type = FieldType.Keyword)
    private Long spuId;

    @Field(
            type = FieldType.Keyword)
    private String skuName;

    @Field(
            type = FieldType.Keyword,
            index = false,
            docValues = false)
    private String brandName;

    @Field(
            type = FieldType.Keyword,
            index = false,
            docValues = false)
    private String categoryName;

    @Field(
            type = FieldType.Long)
    private Long brandId;

    @Field(
            type = FieldType.Long)
    private Long categoryId;

    @Field(
            type = FieldType.Keyword)
    private Integer showPrice;

    @Field()
    private List<String> pictures;

    @Field(
            index = false,
            type = FieldType.Date,
            format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createTime;

    @Field(
            type = FieldType.Date,
            format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updateTime;


    @Field(
            type = FieldType.Nested)
    private List<AttrDoc> attrs;

}
