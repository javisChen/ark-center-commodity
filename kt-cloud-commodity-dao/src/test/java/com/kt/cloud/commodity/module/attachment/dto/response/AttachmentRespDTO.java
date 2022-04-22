package com.kt.cloud.commodity.module.attachment.dto.response;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Data
@ApiModel(value = "AttachmentRespDTO对象", description = "附件表")
public class AttachmentRespDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @ApiModelProperty(value = "业务类型", required = true)
    private String bizType;

    @ApiModelProperty(value = "业务ID", required = true)
    private Long bizId;

    @ApiModelProperty(value = "附件地址", required = true)
    private String url;

}
