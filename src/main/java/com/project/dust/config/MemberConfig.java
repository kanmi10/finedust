package com.project.dust.config;

import com.project.dust.member.repository.MemberMapper;
import com.project.dust.member.repository.MemberRepository;
import com.project.dust.member.repository.MyBatisMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class MemberConfig {

    private final MemberMapper memberMapper;

    @Bean
    public MemberRepository memberRepository() {
        return new MyBatisMemberRepository(memberMapper);
    }

}
