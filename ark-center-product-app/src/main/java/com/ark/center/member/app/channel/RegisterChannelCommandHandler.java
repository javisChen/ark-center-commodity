// 新建渠道相关的应用服务
package com.ark.center.member.app.channel;

import com.ark.center.member.client.channel.command.RegisterChannelCreateCommand;
import com.ark.center.member.infra.channel.RegisterChannel;
import com.ark.center.member.infra.channel.service.RegisterChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterChannelCommandHandler {
    
    private final RegisterChannelService registerChannelService;
    
    @Transactional(rollbackFor = Exception.class)
    public Long createChannel(RegisterChannelCreateCommand command) {
        RegisterChannel channel = new RegisterChannel();
        channel.setChannelCode(command.getChannelCode());
        channel.setChannelName(command.getChannelName());
        channel.setChannelType(command.getChannelType());
        channel.setDescription(command.getDescription());
        channel.setStatus(true);
        
        registerChannelService.save(channel);
        return channel.getId();
    }
} 