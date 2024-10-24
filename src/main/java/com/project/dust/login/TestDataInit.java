package com.project.dust.login;

import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberRepository;
import com.project.dust.domain.member.MemoryMemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class TestDataInit {

    private final MemberRepository memberRepository;

    public TestDataInit() {
        this.memberRepository = new MemoryMemberRepository();
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() throws SQLException {
        memberRepository.save(new Member("test", "김경현", "test!"));
    }

}
