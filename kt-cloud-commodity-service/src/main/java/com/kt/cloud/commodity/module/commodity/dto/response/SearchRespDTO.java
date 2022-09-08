package com.kt.cloud.commodity.module.commodity.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * SearchRespDTO
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Data
@ApiModel(value = "SearchRespDTO", description = "商品搜索结果")
public class SearchRespDTO implements Serializable {

    @ApiModelProperty(value = "商品ID", required = true)
    private Long skuId;
    @ApiModelProperty(value = "商品名称", required = true)
    private String skuName;
    @ApiModelProperty(value = "商品描述", required = true)
    private String description;
    @ApiModelProperty(value = "商品展示图", required = true)
    private String picUrl;

}
