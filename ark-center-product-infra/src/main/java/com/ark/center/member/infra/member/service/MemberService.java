package com.ark.center.member.infra.member.service;

import com.ark.center.member.infra.member.Member;
import com.ark.center.member.infra.member.repository.db.mapper.MemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService extends ServiceImpl<MemberMapper, Member> {

}