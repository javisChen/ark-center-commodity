package com.kt.cloud.commodity.module.attrvalue.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * <p>
 * 商品属性值
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@ApiModel(value = "AttrValueCreateReqDTO对象", description = "商品属性值")
public class AttrValueCreateReqDTO implements Serializable {



    @ApiModelProperty(value = "商品属性ID，关联co_attr.id", required = true)
    @NotEmpty(message = "商品属性ID，关联co_attr.id不能为空")
    private Long attrId;

    @ApiModelProperty(value = "选项值", required = true)
    @NotEmpty(message = "选项值不能为空")
    private String value;

    @ApiModelProperty(value = "类型：enums[COMMON,通用,1;EXCLUSIVE,商品特有,2]", required = true)
    @NotNull(message = "类型：enums[COMMON,通用,1;EXCLUSIVE,商品特有,2]不能为空")
    private Integer type;

}
