package com.ark.center.product.client.attr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品可选项
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@Schema(name = "AttrOptionRespDTO对象", description = "商品可选项")
public class AttrOptionDTO implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "商品属性ID，关联attr.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long attrId;

    @Schema(name = "选项值内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    @Schema(name = "类型：enums[COMMON,通用,1;EXCLUSIVE,商品特有,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

}