package com.project.dust.member.repository;

import com.project.dust.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface MemberMapper {

    void save(Member member);

    Member findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    List<Member> findAll();

    void deleteById(Long memberId);

    void addFavorite(@Param("stationId") Long stationId, @Param("memberId") Long memberId);

    void removeFavorite(Long bookmarkId);

    Long getStationName(String stationName);

    Long hasFavoriteForStation(@Param("memberId") Long memberId, @Param("stationId") Long stationId);

    Set<String> getFavorites(Long memberId);

    boolean isMemberNameDuplicate(String name);

    boolean isMemberIdDuplicate(String loginId);

    boolean isWithdrawMember(String memberId);
}
