package com.project.dust.member.repository;

import com.project.dust.dust.Dust;
import com.project.dust.dust.repository.DustRepository;
import com.project.dust.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DustRepository dustRepository;

    private static final Long[] stationIds = {1L, 2L, 3L};
    private static final String[] stationNames = {"측정소1", "측정소2", "측정소3"};


    @BeforeEach
    void before() {
        Dust dust1 = new Dust();
        dust1.setStationId(stationIds[0]);
        dust1.setStationName(stationNames[0]);
        dust1.setSidoName("서울");

        Dust dust2 = new Dust();
        dust2.setStationId(stationIds[1]);
        dust2.setStationName(stationNames[1]);
        dust2.setSidoName("경기");

        Dust dust3 = new Dust();
        dust3.setStationId(stationIds[2]);
        dust3.setStationName(stationNames[2]);
        dust3.setSidoName("부산");

        dustRepository.save(dust1);
        dustRepository.save(dust2);
        dustRepository.save(dust3);
    }

    @Test
    @DisplayName("회원 저장")
    void save() {
        Member member = new Member("test", "테스트", "test!");
        memberRepository.save(member);
    }

    @Test
    @DisplayName("아이디로 회원 조회")
    void findById() {
        Member member = new Member("test", "테스트", "test!");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회")
    void findByLoginId() {
        String loginId = "test";

        Member member = new Member(loginId, "테스트", "test!");
        memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        assertThat(findMember).isNotEmpty();
        assertThat(findMember.get()).isEqualTo(member);
    }

    @Test
    @DisplayName("모든 회원 조회")
    void findAll() {
        Member member1 = new Member("test1", "테스트1", "test!");
        Member member2 = new Member("test2", "테스트2", "test!");
        Member member3 = new Member("test3", "테스트3", "test!");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> allMembers = memberRepository.findAll();
        assertThat(allMembers).hasSize(3);
    }

    @Test
    @DisplayName("아이디로 회원 삭제")
    void deleteById() {
        Member member = new Member("test1", "테스트1", "test!");

        memberRepository.save(member);

        memberRepository.deleteById(member.getId());

        Member deletedMember = memberRepository.findById(member.getId());

        assertThat(deletedMember.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("즐겨찾기 추가")
    void addFavorite() {
        Long stationId = stationIds[0];
        String stationName = stationNames[0];

        Member member = new Member("test1", "테스트1", "test!");
        memberRepository.save(member);

        memberRepository.addFavorite(stationId, member.getId());

        Set<String> favorites = memberRepository.getFavorites(member.getId());

        assertThat(favorites).hasSize(1);
        assertThat(favorites).contains(stationName);
    }

    @Test
    @DisplayName("즐겨찾기 삭제")
    void removeFavorite() {
        Long stationId = stationIds[0];

        Member member = new Member("test1", "테스트1", "test!");
        memberRepository.save(member);

        memberRepository.addFavorite(stationId, member.getId());

        Long bookmarkId = memberRepository.getBookmarkId(member.getId(), stationId);


        memberRepository.removeFavorite(bookmarkId);

        Set<String> favorites = memberRepository.getFavorites(member.getId());
        assertThat(favorites).isEmpty();
    }


    @Test
    @DisplayName("북마크 ID 가져오기")
    void getBookmarkId() {
        Member member = new Member("test1", "테스트1", "test!");
        memberRepository.save(member);

        memberRepository.addFavorite(stationIds[0], member.getId());

        Long bookmarkId = memberRepository.getBookmarkId(member.getId(), stationIds[0]);

        assertThat(bookmarkId).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("모든 즐겨찾기 조회")
    void getFavorites() {
        Member member = new Member("test1", "테스트1", "test!");
        memberRepository.save(member);

        memberRepository.addFavorite(stationIds[0], member.getId());
        memberRepository.addFavorite(stationIds[1], member.getId());
        memberRepository.addFavorite(stationIds[2], member.getId());

        Set<String> favorites = memberRepository.getFavorites(member.getId());
        assertThat(favorites).hasSize(3);
    }

    @Test
    @DisplayName("회원 이름 중복 검증")
    void isMemberNameDuplicate() {
        String name = "테스트";

        Member member = new Member("test1", name, "test!");
        memberRepository.save(member);

        boolean isDuplicate = memberRepository.isMemberNameDuplicate(name);

        assertThat(isDuplicate).isTrue();
    }

    @Test
    @DisplayName("로그인ID 중복 검증")
    void isMemberIdDuplicate() {
        String loginId = "test@naver.com";

        Member member = new Member(loginId, "테스트", "test!");
        memberRepository.save(member);

        boolean isDuplicate = memberRepository.isMemberIdDuplicate(loginId);

        assertThat(isDuplicate).isTrue();
    }

    @Test
    @DisplayName("탈퇴한 회원 검증")
    void isWithdrawMember() {
        String loginId = "test";
        Member member = new Member(loginId, "테스트", "test!");
        memberRepository.save(member);

        memberRepository.deleteById(member.getId());

        boolean isWithdraw = memberRepository.isWithdrawMember(loginId);

        assertThat(isWithdraw).isTrue();
    }
}