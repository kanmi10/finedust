package com.project.dust.domain.member;

import com.project.dust.member.Member;
import com.project.dust.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findMember() {
        Member member1 = new Member("test1", "테스트1", "test!");
        Member member2 = new Member("test2", "테스트2", "test!");
        Member member3 = new Member("test3", "테스트3", "test!");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

}

