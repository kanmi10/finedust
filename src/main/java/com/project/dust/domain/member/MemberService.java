package com.project.dust.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.project.dust.connection.ConnectionConst.*;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new JdbcMemberRepository(new DriverManagerDataSource(URL, USERNAME , PASSWORD));
    }

    public void join(Member member) {
        memberRepository.save(member);
    }

    public void addFavorite(Long memberId, String stationName) {
        //stationId를 먼저 가져와야 함
        Long stationId = memberRepository.getStationName(stationName);
        log.info("stationId={}", stationId);
        memberRepository.addFavorite(stationId, memberId);
    }

    public void removeFavorite(Long memberId, String stationName) {
        Long stationId = memberRepository.getStationName(stationName);

        // 로그인한 회원이 해당 지역을 북마크했는지 확인
        Long bookmarkId = memberRepository.hasFavoriteForStation(memberId, stationId);

        if (bookmarkId != null) {
            memberRepository.removeFavorite(bookmarkId);
        }

        log.info("북마크 삭제 {}, 회원번호: {} 측정소번호: {}", bookmarkId, memberId, stationId);
    }

    /**
     * 회원가입 아이디 중복검사
     * @param loginId 아이디
     * @return 중복 유무
     */
    public boolean checkIdDuplicate(String loginId) {
        return memberRepository.isMemberIdDuplicate(loginId);
    }

    /**
     * 회원가입 이름 중복검사
     * @param name 이름
     * @return 중복 유무
     */
    public boolean checkNameDuplicate(String name) {
        return memberRepository.isMemberNameDuplicate(name);
    }

    public Set<String> getFavorite(Long memberId) {
        return memberRepository.getFavorites(memberId);
    }

    /**
     * 회원탈퇴
     * @param memberId 회원번호
     */
    public void markAsDeleted(Long memberId) {
        memberRepository.deleteById(memberId);
    }



}
