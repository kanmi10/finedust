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
        return Optional.empty();
    }

    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public List<Board> findBoards(int limit, int offset) {
        return List.of();
    }

    @Override
    public List<Board> findBoards(BoardSearchDTO boardSearchDTO) {
        return boardMapper.findBoards(boardSearchDTO);
    }

    @Override
    public int countAllBoards() {
        return 0;
    }

    @Override
    public int countAllBoards(BoardSearchDTO boardSearchDTO) {
        return boardMapper.countAllBoards(boardSearchDTO);
    }

    @Override
    public int countBoardsByKeyword(String typeName, String keyword) {
        return 0;
    }

    @Override
    public int countBoardsBySidoId(Long sidoId) {
        return 0;
    }

    @Override
    public int countBoardsByTitle(String title) {
        return 0;
    }

    @Override
    public int countBoardsByContent(String content) {
        return 0;
    }

    @Override
    public int countBoardsByTitleAndContent(String title_content) {
        return 0;
    }

    @Override
    public int countBoardsByName(String name) {
        return 0;
    }

    @Override
    public Optional<Board> findByMemberId(Long memberId) {
        return Optional.empty();
    }

    @Override
    public List<Board> searchBySidoId(Long sidoId, int limit, int offset) {
        return List.of();
    }

    @Override
    public List<Board> searchByTitle(String title, int limit, int offset) {
        return List.of();
    }

    @Override
    public List<Board> searchByContent(String content, int limit, int offset) {
        return List.of();
    }

    @Override
    public List<Board> searchByTitleAndContent(String title_content, int limit, int offset) {
        return List.of();
    }

    @Override
    public List<Board> searchByName(String name, int limit, int offset) {
        return List.of();
    }

    @Override
    public void update(Board board) {
        boardMapper.update(board);
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
