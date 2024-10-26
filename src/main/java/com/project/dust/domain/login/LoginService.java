package com.project.dust.domain.login;

import com.project.dust.domain.member.JdbcMemberRepository;
import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

import static com.project.dust.connection.ConnectionConst.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService() {
        this.memberRepository = new JdbcMemberRepository(new DriverManagerDataSource(URL, USERNAME , PASSWORD));
    }

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }

}
