package com.project.dust.domain.board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    // Create
    void save(Board board);

    // Read
    Optional<Board> findById(Long boardId);
    List<Board> findAll();

    List<Board> findBoards(int limit, int offset);

    int countBoards();


    Optional<Board> findByMemberId(Long memberId);
    Optional<Board> findBySidoId(Long sidoId);
    Optional<Board> searchByTitle(String keyword);

    // Update
    void update(Board board);

    // Delete
    void deleteById(Long boardId);
    void deleteByMemberId(Long memberId);
    void deleteAll();
}
