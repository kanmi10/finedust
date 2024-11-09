package com.project.dust.domain.board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    // Create
    void save(Board board);

    // Read
    Optional<Board> findById(Long boardId);
    List<Board> findAll();
    List<Board> findByMemberId(Long memberId);
    List<Board> findBySidoId(Long sidoId);
    List<Board> searchByTitle(String keyword);

    // Update
    void update(Board board);

    // Delete
    void deleteById(Long boardId);
    void deleteByMemberId(Long memberId);
    void deleteAll();
}
