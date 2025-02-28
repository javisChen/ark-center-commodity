package com.ark.center.member.adapter.channel.web;

import com.ark.center.member.app.channel.RegisterChannelCommandHandler;
import com.ark.center.member.client.channel.RegisterChannelApi;
import com.ark.center.member.client.channel.command.RegisterChannelCreateCommand;
import com.ark.component.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/register-channels")
@RequiredArgsConstructor
public class RegisterChannelController implements RegisterChannelApi {

    private final RegisterChannelCommandHandler registerChannelCommandHandler;

    @Override
    public SingleResponse<Long> createChannel(RegisterChannelCreateCommand command) {
        Long channelId = registerChannelCommandHandler.createChannel(command);
        return SingleResponse.ok(channelId);
    }
}