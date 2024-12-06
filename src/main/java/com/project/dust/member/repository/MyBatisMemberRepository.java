package com.project.dust.member.repository;

import com.project.dust.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisMemberRepository implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.save(member);
        log.info("member.getId() = {}", member.getId());
    }

    @Override
    public Member findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return memberMapper.findByLoginId(loginId);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public void deleteById(Long memberId) {
        memberMapper.deleteById(memberId);
    }

    @Override
    public void addFavorite(Long stationId, Long memberId) {
        memberMapper.addFavorite(stationId, memberId);
    }

    @Override
    public void removeFavorite(Long bookmarkId) {
        memberMapper.removeFavorite(bookmarkId);
    }

    @Override
    public Long getStationId(String stationName) {
        return memberMapper.getStationName(stationName);
    }

    @Override
    public Long getBookmarkId(Long memberId, Long stationId) {
        return memberMapper.getBookmarkId(memberId, stationId);
    }

    @Override
    public Set<String> getFavorites(Long memberId) {
        return memberMapper.getFavorites(memberId);
    }

    @Override
    public boolean isMemberNameDuplicate(String name) {
        return memberMapper.isMemberNameDuplicate(name);
    }

    @Override
    public boolean isMemberIdDuplicate(String loginId) {
        return memberMapper.isMemberIdDuplicate(loginId);
    }

    @Override
    public boolean isWithdrawMember(String memberId) {
        return memberMapper.isWithdrawMember(memberId);
    }

    @Override
    public boolean isBookmarks(Long memberId, Long stationId) {
        return memberMapper.isBookmarks(memberId, stationId);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }
}
