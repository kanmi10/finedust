package com.project.dust.board.repository;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    // Create
    void save(Board board);

    // Read
    Optional<Board> findById(Long boardId);
    List<Board> findAll();

    List<Board> findBoards(int limit, int offset);
    List<Board> findBoards(BoardSearchDTO boardSearchDTO);

    int countAllBoards();
    int countAllBoards(BoardSearchDTO boardSearchDTO);
    int countBoardsByKeyword(String typeName, String keyword);
    int countBoardsBySidoId(Long sidoId);
    int countBoardsByTitle(String title);
    int countBoardsByContent(String content);
    int countBoardsByTitleAndContent(String title_content);
    int countBoardsByName(String name);


    Optional<Board> findByMemberId(Long memberId);
    List<Board> searchBySidoId(Long sidoId, int limit, int offset);
    List<Board> searchByTitle(String title, int limit, int offset);
    List<Board> searchByContent(String content, int limit, int offset);
    List<Board> searchByTitleAndContent(String title_content, int limit, int offset);
    List<Board> searchByName(String name, int limit, int offset);


    // Update
    void update(Board board);

    // Delete
    void deleteById(Long boardId);
    void deleteByMemberId(Long memberId);
    void deleteAll();
}
