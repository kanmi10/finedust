package com.project.dust.board.repository;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper
public interface BoardMapper {
    void save(Board board);

    void update(Board board);

    List<Board> findBoards(BoardSearchDTO boardSearchDTO);

    int countAllBoards(BoardSearchDTO boardSearchDTO);

    Optional<Board> findById(Long boardId);

    void deleteById(Long boardId);

    String getSidoName(Long sidoId);
}
