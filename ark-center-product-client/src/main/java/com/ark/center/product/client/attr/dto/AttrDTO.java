package com.ark.center.product.client.attr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Data
@Schema(name = "AttrRespDTO对象", description = "商品属性")
public class AttrDTO implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "属性名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "属性录入方式，enums[INPUT,手工录入,1;SELECT,从选项列表选取,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer inputType;

    @Schema(name = "属性的类型，enums[SPEC,规格,1;PARAM,参数,2]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

    @Schema(name = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(name = "模板ID，关联attr_template.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long attrTemplateId;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "是否支持手动新增，enums[NO,不支持,0;YES,支持,1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer canManualAdd;

    @Schema(name = "属性组ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long attrGroupId;

    @Schema(name = "属性可选项列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<AttrOptionDTO> optionList;

}
