package com.project.dust.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import static com.project.dust.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();
    private final MemberRepository memberRepository;

    public MemberServiceTest() {
        this.memberRepository = new JdbcMemberRepository(new DriverManagerDataSource(URL, USERNAME , PASSWORD));
    }

    @Test
    @DisplayName("회원 북마크 생성 및 삭제")
    void favorite() {
        Long memberId = 1L;
        String stationName = "수지";
        memberService.addFavorite(memberId, stationName);
        memberService.removeFavorite(memberId, stationName);
    }

}