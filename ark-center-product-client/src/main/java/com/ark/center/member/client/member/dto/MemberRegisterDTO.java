package com.ark.center.member.client.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "会员注册返回信息")
public class MemberRegisterDTO {
    
    @Schema(description = "会员ID")
    private Long memberId;
    
    @Schema(description = "会员编号")
    private String memberNo;
    
    // 后续可以方便添加其他需要返回的字段
} 