package com.project.dust.domain.login;

import com.project.dust.domain.member.JdbcMemberRepository;
import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberRepository;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService() {
        this.memberRepository = new JdbcMemberRepository(new HikariDataSource());
    }

    public Member login(String loginId, String password) throws SQLException {
        return memberRepository.findByLoginId(loginId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }

}
