package com.project.dust.domain.member;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    List<Member> findAll();

    void delete(Long memberId);

    void addFavorite(Long stationId, Long memberId);

    void removeFavorite(Long bookmarkId);

    Long getStationName(String stationName);

    Long hasFavoriteForStation(Long memberId, Long stationId);

    Set<String> getFavorites(Long memberId);

    boolean isMemberNameDuplicate(String name);

    boolean isMemberIdDuplicate(String loginId);
}
