package com.kt.cloud.commodity.module.attrspu.dto.response;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * SPU特有的商品属性选项
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "AttrSpuRespDTO对象", description = "SPU特有的商品属性选项")
public class AttrSpuRespDTO implements Serializable {


    @ApiModelProperty(value = "商品属性ID，关联co_attr.id", required = true)
    private Long attrId;

    @ApiModelProperty(value = "SPU ID，关联co_spu.id", required = true)
    private Long spuId;

    @ApiModelProperty(value = "选项列表，有多个选项以逗号（,）分隔", required = true)
    private String options;

}
