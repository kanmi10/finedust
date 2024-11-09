package com.project.dust.domain.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcBoardRepository implements BoardRepository {
    @Override
    public void save(Board board) {

    }

    @Override
    public Optional<Board> findById(Long boardId) {
        return Optional.empty();
    }

    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public List<Board> findByMemberId(Long memberId) {
        return List.of();
    }

    @Override
    public List<Board> findBySidoId(Long sidoId) {
        return List.of();
    }

    @Override
    public List<Board> searchByTitle(String keyword) {
        return List.of();
    }

    @Override
    public void update(Board board) {

    }

    @Override
    public void deleteById(Long boardId) {

    }

    @Override
    public void deleteByMemberId(Long memberId) {

    }

    @Override
    public void deleteAll() {

    }
}
