package com.project.dust.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    List<Member> findAll();

    void delete(Long memberId);

    void favoriteStation(Long stationId, Long memberId);
}
