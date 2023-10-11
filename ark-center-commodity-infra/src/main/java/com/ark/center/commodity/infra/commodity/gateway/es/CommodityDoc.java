package com.ark.center.commodity.infra.commodity.gateway.es;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "commodity")
@Data
public class CommodityDoc {

    @Id
    private Long id;

    private String name;

    private String description;

    private String brandName;

    private String categoryName;

    private Long brandId;

    private Long categoryId;

    private Long showPrice;

    @Field(index = false,
            store = true,
            type = FieldType.Date,
            format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime gmtCreate;

    private Integer shelfStatus;
}
