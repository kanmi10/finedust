package com.project.dust.domain.member;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;


import static com.project.dust.connection.ConnectionConst.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new JdbcMemberRepository(new DriverManagerDataSource(URL, USERNAME , PASSWORD));
    }

    public void join(Member member) {
        memberRepository.save(member);
    }
}
