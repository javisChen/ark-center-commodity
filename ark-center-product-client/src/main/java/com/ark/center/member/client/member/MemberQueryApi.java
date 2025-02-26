package com.ark.center.member.client.member;

import com.ark.center.member.client.member.dto.MemberAuthDTO;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员查询相关API
 */
public interface MemberQueryApi {
    
    /**
     * 根据用户名或手机号查询会员认证信息
     * 
     * @param identity 用户名或手机号
     * @return 会员认证信息
     */
    @GetMapping("/auth-info")
    @Operation(summary = "获取会员认证信息", description = "根据用户名或手机号查询会员认证信息，供认证中心使用")
    SingleResponse<MemberAuthDTO> getMemberAuthInfo(
            @Parameter(description = "用户名或手机号") @RequestParam String identity);
} 