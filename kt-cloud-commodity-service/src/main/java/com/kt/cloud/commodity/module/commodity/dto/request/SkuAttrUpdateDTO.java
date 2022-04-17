package com.kt.cloud.commodity.module.commodity.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuAttrUpdateDTO {

    @ApiModelProperty(value = "属性ID", required = false)
    private Long attrId;
    @ApiModelProperty(value = "属性值", required = false)
    private String attrValue;
}
