package com.project.dust.board.repository;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BoardMapper {
    // Create
    void save(Board board);

    // Update
    void update(Board board);

    List<Board> findBoards(BoardSearchDTO boardSearchDTO);

    int countAllBoards(BoardSearchDTO boardSearchDTO);
}
