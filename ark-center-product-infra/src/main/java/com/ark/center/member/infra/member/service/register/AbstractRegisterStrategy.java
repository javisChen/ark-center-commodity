package com.ark.center.member.infra.member.service.register;

import com.ark.center.member.client.member.command.MemberRegisterCommand;
import com.ark.center.member.client.member.common.IdentityType;
import com.ark.center.member.client.member.common.LevelUpgradeType;
import com.ark.center.member.client.member.common.LevelValidityType;
import com.ark.center.member.client.member.common.MemberStatus;
import com.ark.center.member.client.member.common.PointsAccountStatus;
import com.ark.center.member.infra.member.Member;
import com.ark.center.member.infra.member.MemberAuth;
import com.ark.center.member.infra.member.MemberLevelConfig;
import com.ark.center.member.infra.member.MemberLevelRecord;
import com.ark.center.member.infra.member.MemberPointsAccount;
import com.ark.center.member.infra.member.service.MemberAuthService;
import com.ark.center.member.infra.member.service.MemberLevelRecordService;
import com.ark.center.member.infra.member.service.MemberLevelService;
import com.ark.center.member.infra.member.service.MemberPointsAccountService;
import com.ark.center.member.infra.member.service.MemberService;
import com.ark.component.exception.BizException;
import com.ark.component.security.base.password.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRegisterStrategy implements RegisterStrategy {

    protected final MemberService memberService;
    protected final MemberAuthService memberAuthService;
    protected final PasswordService passwordService;
    protected final MemberLevelService memberLevelService;
    protected final MemberLevelRecordService memberLevelRecordService;
    
    @Autowired
    protected MemberPointsAccountService memberPointsAccountService;

    @Override
    public void validate(MemberRegisterCommand command) {
        // 1. 验证认证标识是否已存在
        if (memberAuthService.isIdentifierExists(getIdentityType(), getIdentifier(command))) {
            throw new BizException(getIdentityType().getDesc() + "已被注册");
        }
        
        // 2. 子类特定验证
        doValidate(command);
    }

    @Override
    public Member doRegister(MemberRegisterCommand command) {
        // 1. 创建并保存会员基础信息
        Member member = saveMember(command);
        
        // 2. 创建并保存认证信息
        saveMemberAuth(command, member.getId());
        
        // 3. 创建并保存等级记录
        saveLevelRecord(member.getId(), member.getLevel());
        
        // 4. 创建并保存积分账户
        savePointsAccount(member.getId());
        
        return member;
    }

    /**
     * 保存会员基础信息
     */
    protected Member saveMember(MemberRegisterCommand command) {
        Member member = new Member();
        member.setStatus(MemberStatus.ENABLE);
        member.setNickname(generateDefaultNickname());
        member.setMobile(command.getMobile());
        
        // 获取默认等级配置
        MemberLevelConfig defaultLevel = memberLevelService.getDefaultLevel();
        if (defaultLevel == null) {
            throw new BizException("会员等级配置异常");
        }
        
        // 设置等级相关信息
        member.setLevel(defaultLevel.getLevel());
        member.setGrowthValue(0L);
        member.setPoints(0L);
        
        // 保存会员信息
        memberService.save(member);
        return member;
    }

    /**
     * 保存认证信息
     */
    protected void saveMemberAuth(MemberRegisterCommand command, Long memberId) {
        MemberAuth auth = new MemberAuth();
        auth.setMemberId(memberId);
        auth.setCredential(passwordService.enhancePassword(command.getPassword()));
        auth.setIdentityType(getIdentityType());
        auth.setIdentifier(getIdentifier(command));
        
        // 保存认证信息
        memberAuthService.save(auth);
    }

    /**
     * 保存等级记录
     */
    protected void saveLevelRecord(Long memberId, Integer level) {
        // 获取默认等级配置
        MemberLevelConfig defaultLevel = memberLevelService.getDefaultLevel();
        if (defaultLevel == null) {
            throw new BizException("会员等级配置异常");
        }
        
        MemberLevelRecord levelRecord = new MemberLevelRecord();
        levelRecord.setMemberId(memberId);
        levelRecord.setBeforeLevel(0);
        levelRecord.setAfterLevel(level);
        levelRecord.setUpgradeType(LevelUpgradeType.INIT);
        levelRecord.setEffectTime(LocalDateTime.now());
        if (defaultLevel.getValidityType() != LevelValidityType.PERMANENT) {
            levelRecord.setExpireTime(calculateExpireTime(defaultLevel));
        }
        levelRecord.setDescription("会员注册初始等级");
        memberLevelRecordService.save(levelRecord);
    }

    /**
     * 保存积分账户
     */
    protected void savePointsAccount(Long memberId) {
        log.info("开始创建会员积分账户: memberId={}", memberId);
        
        MemberPointsAccount account = memberPointsAccountService.getOrCreateAccount(memberId);
        
        log.info("会员积分账户创建完成: memberId={}, accountId={}", memberId, account.getId());
    }

    /**
     * 获取认证类型
     */
    protected abstract IdentityType getIdentityType();

    /**
     * 获取认证标识
     */
    protected abstract String getIdentifier(MemberRegisterCommand command);

    /**
     * 子类实现具体的验证逻辑
     */
    protected abstract void doValidate(MemberRegisterCommand command);

    /**
     * 生成默认昵称：用户+12位随机字符串
     */
    protected String generateDefaultNickname() {
        return "用户" + UUID.randomUUID().toString().substring(0, 12);
    }

    /**
     * 计算等级过期时间
     */
    protected LocalDateTime calculateExpireTime(MemberLevelConfig levelConfig) {
        LocalDateTime now = LocalDateTime.now();
        
        if (levelConfig.getValidityType() == LevelValidityType.FIXED) {
            // 固定期限
            return levelConfig.getValidEndTime();
        } else if (levelConfig.getValidityType() == LevelValidityType.PERIODIC) {
            // 周期性
            switch (levelConfig.getPeriodType()) {
                case 1: // 天
                    return now.plusDays(levelConfig.getPeriodValue());
                case 2: // 周
                    return now.plusWeeks(levelConfig.getPeriodValue());
                case 3: // 月
                    return now.plusMonths(levelConfig.getPeriodValue());
                case 4: // 年
                    return now.plusYears(levelConfig.getPeriodValue());
                default:
                    throw new BizException("无效的周期类型");
            }
        }
        
        // 永久有效返回null
        return null;
    }
} 