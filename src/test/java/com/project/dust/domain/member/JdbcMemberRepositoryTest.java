package com.project.dust.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcMemberRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(JdbcMemberRepositoryTest.class);
    MemberRepository memberRepository = new JdbcMemberRepository();

    @Test
    void save() throws SQLException {
        Member member = new Member("admin", "관리자", "admin!");
        memberRepository.save(member);
    }

    @Test
    void findById() throws SQLException {
        Member findMember = memberRepository.findById(1L);
        Assertions.assertThat(findMember).isNotNull();
    }

    @Test
    void findByLoginId() throws SQLException {
        Optional<Member> findMembers = memberRepository.findByLoginId("admin");
        log.info("findMembers={}", findMembers);
        Assertions.assertThat(findMembers).isNotNull();
    }

    @Test
    void findAll() throws SQLException {
        List<Member> members = memberRepository.findAll();
        log.info("members={}", members);
        Assertions.assertThat(members).isNotNull();
    }
}