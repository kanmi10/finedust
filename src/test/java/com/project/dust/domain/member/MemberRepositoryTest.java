package com.project.dust.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
class MemberRepositoryTest {

    MemberRepository memberRepository = new JdbcMemberRepository();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("admin", "관리자", "admin!");
        memberRepository.save(member);

        //findById
        Member findMember = memberRepository.findById(member.getId());
        Assertions.assertThat(member).isEqualTo(findMember);

        //findByLoginId
        Optional<Member> members = memberRepository.findByLoginId(member.getLoginId());
        Member member1 = members.filter(m ->
                        m.getLoginId().equals(member.getLoginId())).get();

        Assertions.assertThat(member).isEqualTo(member1);

        //delete
        memberRepository.delete(member.getId());
        Member deleteMember = memberRepository.findById(member.getId());
        Assertions.assertThat(deleteMember).isNull();
    }
}