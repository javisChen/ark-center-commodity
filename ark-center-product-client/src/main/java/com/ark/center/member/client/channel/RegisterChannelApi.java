package com.ark.center.member.client.channel;

import com.ark.center.member.client.channel.command.RegisterChannelCreateCommand;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "${ark.center.member.service.name:product}",
        path = "/v1/members",
        url = "${ark.center.member.service.uri:}",
        dismiss404 = true
)
@Tag(name = "注册渠道管理接口")
public interface RegisterChannelApi {

    @PostMapping
    @Operation(summary = "创建注册渠道")
    SingleResponse<Long> createChannel(@RequestBody @Validated RegisterChannelCreateCommand command);
}