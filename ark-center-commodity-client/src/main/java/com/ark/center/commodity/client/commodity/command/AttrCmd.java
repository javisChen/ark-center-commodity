package com.ark.center.commodity.client.commodity.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 规格属性DTO
 * @author jc
 */
@Data
public class AttrCmd {

    @Schema(name = "属性Id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long attrId;
    @Schema(name = "属性名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String attrName;
    @Schema(name = "属性值", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String attrValue;
}
