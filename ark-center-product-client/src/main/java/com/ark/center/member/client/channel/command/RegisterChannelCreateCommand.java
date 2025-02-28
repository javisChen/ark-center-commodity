package com.ark.center.member.client.channel.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "注册渠道创建命令")
public class RegisterChannelCreateCommand {
    
    @Schema(description = "渠道编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "渠道编码不能为空")
    private String channelCode;
    
    @Schema(description = "渠道名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "渠道名称不能为空")
    private String channelName;
    
    @Schema(description = "渠道类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "渠道类型不能为空")
    private String channelType;
    
    @Schema(description = "渠道描述")
    private String description;
} 