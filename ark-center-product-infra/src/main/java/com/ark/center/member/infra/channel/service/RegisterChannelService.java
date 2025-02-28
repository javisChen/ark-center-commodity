package com.ark.center.member.infra.channel.service;

import com.ark.center.member.infra.channel.RegisterChannel;
import com.ark.center.member.infra.channel.repository.db.mapper.RegisterChannelMapper;
import com.ark.component.exception.BizException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterChannelService extends ServiceImpl<RegisterChannelMapper, RegisterChannel> {

    /**
     * 根据渠道编码获取渠道信息
     */
    public RegisterChannel getByCode(String channelCode) {
        return lambdaQuery()
                .eq(RegisterChannel::getChannelCode, channelCode)
                .one();
    }
    
    /**
     * 验证渠道是否有效
     */
    public void validateChannel(String channelCode) {
        if (channelCode == null) {
            return;
        }
        
        RegisterChannel channel = getByCode(channelCode);
        if (channel == null) {
            log.warn("注册渠道不存在: channelCode={}", channelCode);
            throw new BizException("无效的注册渠道");
        }
        
        if (!channel.getStatus()) {
            log.warn("注册渠道已禁用: channelCode={}", channelCode);
            throw new BizException("该注册渠道已禁用");
        }
    }
} 