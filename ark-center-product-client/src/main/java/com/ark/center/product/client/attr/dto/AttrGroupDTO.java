package com.ark.center.product.client.attr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "AttrGroupRespDTO对象", description = "商品属性组")
public class AttrGroupDTO implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "属性分组名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "模板ID，关联co_attr_template.id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long attrTemplateId;

    @Schema(name = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime gmtCreate;

    @Schema(name = "属性列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<AttrDTO> attrList;

}
