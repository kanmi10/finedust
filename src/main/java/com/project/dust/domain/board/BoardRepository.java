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
    int countBoardsByKeyword(String typeName, String keyword);


    Optional<Board> findByMemberId(Long memberId);
    Optional<Board> findBySidoId(Long sidoId);
    List<Board> searchByTitle(String keyword, int limit, int offset);
    List<Board> searchByContent(String keyword, int limit, int offset);
    List<Board> searchByTitleAndContent(String keyword, int limit, int offset);
    List<Board> searchByName(String name, int limit, int offset);


    // Update
    void update(Board board);

    // Delete
    void deleteById(Long boardId);
    void deleteByMemberId(Long memberId);
    void deleteAll();
}
