package com.ark.center.commodity.client.attr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品属性模板
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "AttrTemplateRespDTO对象", description = "商品属性模板")
public class AttrTemplateDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "模板名称", required = true)
    private String name;

    @ApiModelProperty(value = "规格数量", required = true)
    private Integer specCount;

    @ApiModelProperty(value = "参数数量", required = true)
    private Integer paramCount;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime gmtCreate;

}
