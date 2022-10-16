package com.ark.center.commodity.client.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Data
@ApiModel(value = "CategoryRespDTO对象", description = "商品类目")
public class CategoryDTO implements Serializable {

    @ApiModelProperty(value = "分类id", required = true)
    private Long id;

    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @ApiModelProperty(value = "商品数量", required = false)
    private Integer commodityCount;

    @ApiModelProperty(value = "是否显示 enums[NO,否,0;YES,是,1]", required = true)
    private Integer isShow;

    @ApiModelProperty(value = "是否导航 enums[NO,否,0;YES,是,1]", required = true)
    private Integer isNav;

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    @ApiModelProperty(value = "上级ID", required = true)
    private Long pid;

    @ApiModelProperty(value = "规格参数模板ID", required = true)
    private Long attrTemplateId;

    @ApiModelProperty(value = "分类级别", required = true)
    private Integer level;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime gmtCreate;

}
