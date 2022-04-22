package com.kt.cloud.commodity.module.commodity.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuAttrUpdateDTO {

    @ApiModelProperty(value = "属性Id", required = false)
    private Long attrId;
    @ApiModelProperty(value = "属性名称", required = false)
    private Long attrName;
    @ApiModelProperty(value = "属性值", required = false)
    private String attrValue;
}
