package com.project.dust.board.repository;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisBoardRepository implements BoardRepository {

    private final BoardMapper boardMapper;

    @Override
    public void save(Board board) {
        boardMapper.save(board);
    }


    @Override
    public Optional<Board> findById(Long boardId) {
        return boardMapper.findById(boardId);
    }


    @Override
    public List<Board> findBoards(BoardSearchDTO boardSearchDTO) {
        return boardMapper.findBoards(boardSearchDTO);
    }


    @Override
    public int countAllBoards(BoardSearchDTO boardSearchDTO) {
        return boardMapper.countAllBoards(boardSearchDTO);
    }


    @Override
    public void update(Board board) {
        boardMapper.update(board);
    }

    @Override
    public void deleteById(Long boardId) {
        boardMapper.deleteById(boardId);
    }


    @Override
    public String getSidoName(Long sidoId) {
        return boardMapper.getSidoName(sidoId);
    }
}
