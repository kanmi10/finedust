package com.project.dust.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Long memberId) {

    }

    @Override
    public void addFavorite(Long stationId, Long memberId) {

    }

    @Override
    public void removeFavorite(Long bookmarkId) {

    }

    @Override
    public Long getStationName(String stationName) {
        return 0L;
    }

    @Override
    public Long hasFavoriteForStation(Long memberId, Long stationId) {
        return 0L;
    }

    @Override
    public Set<String> getFavorites(Long memberId) {
        return null;
    }

    public void clearStore() {
        store.clear();
    }

}
