package com.kt.cloud.commodity.module.category.dto.response;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品类目
 * </p>
 *
 * @author EOP
 * @since 2022-03-02
 */
@Data
@ApiModel(value="CategoryRespDTO对象", description="商品类目")
public class CategoryRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @ApiModelProperty(value = "商品数量", required = false)
    private Integer commodityNum;

    @ApiModelProperty(value = "是否显示 enums[NO,否,0;YES,是,1]", required = true)
    private Boolean isShow;

    @ApiModelProperty(value = "是否导航 enums[NO,否,0;YES,是,1]", required = true)
    private Boolean isMenu;

    @ApiModelProperty(value = "排序", required = true)
    private Integer seq;

    @ApiModelProperty(value = "上级ID", required = true)
    private Integer parentId;

    @ApiModelProperty(value = "模板ID", required = true)
    private Integer templateId;

    @ApiModelProperty(value = "删除标识 0-表示未删除 大于0-已删除", required = true)
    private Long isDeleted;

}
