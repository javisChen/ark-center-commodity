package com.kt.cloud.commodity.module.commodity.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpuAttrUpdateDTO {

    @ApiModelProperty(value = "属性Id", required = false)
    private Long attrId;
    @ApiModelProperty(value = "属性名称", required = false)
    private String attrName;
    @ApiModelProperty(value = "属性值", required = false)
    private String attrValue;

}
