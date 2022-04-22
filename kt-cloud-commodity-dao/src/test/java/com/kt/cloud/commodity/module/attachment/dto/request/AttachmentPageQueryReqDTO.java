package com.kt.cloud.commodity.module.attachment.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.kt.component.dto.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 附件表
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AttachmentPageQueryReqDTO对象", description = "附件表")
public class AttachmentPageQueryReqDTO extends PagingQuery {


    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "业务ID")
    private Long bizId;

    @ApiModelProperty(value = "附件地址")
    private String url;

}
