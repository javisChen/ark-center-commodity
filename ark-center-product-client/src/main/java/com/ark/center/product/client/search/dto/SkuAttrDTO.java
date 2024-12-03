package com.ark.center.product.client.search.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SKU属性", description = "SKU属性")
public class SkuAttrDTO {

    private Long attrId;

    private String attrName;

    private String attrValue;

}
