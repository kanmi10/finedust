package com.project.dust.login;

import com.project.dust.member.Member;
import com.project.dust.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
