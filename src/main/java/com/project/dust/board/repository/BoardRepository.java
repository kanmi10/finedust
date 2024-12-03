package com.project.dust.board.repository;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    //Create
    void save(Board board);

    //Read
    Optional<Board> findById(Long boardId);
    List<Board> findBoards(BoardSearchDTO boardSearchDTO);
    List<Board> findAllBoards(BoardSearchDTO boardSearchDTO);
    String getSidoName(Long sidoId);

    int countAllBoards(BoardSearchDTO boardSearchDTO);

    // Update
    void update(Board board);

    // Delete
    void deleteById(Long boardId);


}
