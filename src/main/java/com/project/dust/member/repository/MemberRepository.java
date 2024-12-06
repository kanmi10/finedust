package com.project.dust.member.repository;

import com.project.dust.member.Member;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    List<Member> findAll();

    void deleteById(Long memberId);

    void addFavorite(Long stationId, Long memberId);

    void removeFavorite(Long bookmarkId);

    Long getStationId(String stationName);

    Long getBookmarkId(Long memberId, Long stationId);

    Set<String> getFavorites(Long memberId);

    boolean isMemberNameDuplicate(String name);

    boolean isMemberIdDuplicate(String loginId);

    boolean isWithdrawMember(String memberId);

    boolean isBookmarks(Long memberId, Long stationId);

    Optional<Member> findByEmail(String email);
}
