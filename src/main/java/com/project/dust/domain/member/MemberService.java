package com.project.dust.domain.member;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new JdbcMemberRepository(new HikariDataSource());
    }

    public void join(Member member) throws SQLException {
        memberRepository.save(member);
    }
}
