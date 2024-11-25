package com.project.dust.domain.member;

import com.project.dust.member.Member;
import com.project.dust.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@Slf4j
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void crud() {
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
        memberRepository.deleteById(member.getId());
        Member deleteMember = memberRepository.findById(member.getId());
        Assertions.assertThat(deleteMember).isNull();
    }

}