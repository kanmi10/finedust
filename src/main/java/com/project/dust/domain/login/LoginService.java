package com.project.dust.domain.login;

import com.project.dust.domain.member.JdbcMemberRepository;
import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.NoSuchElementException;

import static com.project.dust.connection.ConnectionConst.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginService() {
        this.memberRepository = new JdbcMemberRepository(new DriverManagerDataSource(URL, USERNAME, PASSWORD));
    }

    /**
     * 회원 로그인
     * @param loginId  로그인ID
     * @param password 비밀번호
     * @return 회원 객체
     */
    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        //회원탈퇴 유무 검사
        if (member != null) {
            if (member.isDeleted()) {
                throw new NoSuchElementException("탈퇴한 계정입니다.");
            }
        }

        return member;
    }

}
