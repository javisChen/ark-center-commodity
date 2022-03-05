package com.kt.cloud.commodity.module.attrspu.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
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
@ApiModel(value = "AttrSpuUpdateReqDTO对象", description = "SPU特有的商品属性选项")
public class AttrSpuUpdateReqDTO implements Serializable {


    @ApiModelProperty(value = "商品属性ID，关联co_attr.id", required = true)
    @NotEmpty(message = "商品属性ID，关联co_attr.id不能为空")
    private Long attrId;

    @ApiModelProperty(value = "SPU ID，关联co_spu.id", required = true)
    @NotEmpty(message = "SPU ID，关联co_spu.id不能为空")
    private Long spuId;

    @ApiModelProperty(value = "选项列表，有多个选项以逗号（,）分隔", required = true)
    @NotEmpty(message = "选项列表，有多个选项以逗号（,）分隔不能为空")
    private String options;

}