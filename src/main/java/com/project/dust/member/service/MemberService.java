package com.project.dust.member.service;

import com.project.dust.dust.repository.DustRepository;
import com.project.dust.member.Member;
import com.project.dust.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final DustRepository dustRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(Member member) {
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }

    public void addFavorite(Long memberId, String stationName) {
        //stationId를 먼저 가져와야 함
        Long stationId = dustRepository.getStationId(stationName);
        log.info("stationId={}", stationId);
        memberRepository.addFavorite(stationId, memberId);
    }

    public void removeFavorite(Long memberId, String stationName) {
        Long stationId = memberRepository.getStationId(stationName);

        // 로그인 회원이 해당 지역을 북마크했는지 확인
        Long bookmarkId = memberRepository.getBookmarkId(memberId, stationId);

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
