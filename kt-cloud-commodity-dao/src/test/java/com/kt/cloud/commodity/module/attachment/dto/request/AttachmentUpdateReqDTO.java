package com.kt.cloud.commodity.module.attachment.dto.request;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
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
@ApiModel(value = "AttachmentUpdateReqDTO对象", description = "附件表")
public class AttachmentUpdateReqDTO implements Serializable {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "业务类型", required = true)
    @NotEmpty(message = "业务类型不能为空")
    private String bizType;

    @ApiModelProperty(value = "业务ID", required = true)
    @NotNull(message = "业务ID不能为空")
    private Long bizId;

    @ApiModelProperty(value = "附件地址", required = true)
    @NotEmpty(message = "附件地址不能为空")
    private String url;

}