package com.ark.center.member.client.member.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    enumAsRef = true, 
    description = """
        会员状态:
         * `NORMAL` - 正常
         * `DISABLED` - 禁用
         * `LOCKED` - 锁定
        """
)
public enum MemberStatus {
    NORMAL("正常"),
    DISABLED("禁用"),
    LOCKED("锁定");

    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }
}