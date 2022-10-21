package com.ark.center.commodity.client.attr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品属性组
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "AttrGroupRespDTO对象", description = "商品属性组")
public class AttrGroupDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "属性分组名称", required = true)
    private String name;

    @ApiModelProperty(value = "模板ID，关联co_attr_template.id", required = true)
    private Long attrTemplateId;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "属性列表", required = false)
    private List<AttrDTO> attrList;

}
