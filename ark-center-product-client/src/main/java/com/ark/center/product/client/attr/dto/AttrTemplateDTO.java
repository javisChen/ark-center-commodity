package com.ark.center.product.client.attr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "AttrTemplateRespDTO对象", description = "商品属性模板")
public class AttrTemplateDTO implements Serializable {

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(name = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "规格数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer specCount;

    @Schema(name = "参数数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer paramCount;

    @Schema(name = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime gmtCreate;

}
