package com.project.dust.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // Create
    public void createBoard(Board board) {
        boardRepository.save(board);
    }

    // Read
    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Optional<Board> getBoardsByMemberId(Long memberId) {
        return boardRepository.findByMemberId(memberId);
    }

    public Optional<Board> getBoardsBySidoId(Long sidoId) {
        return boardRepository.findBySidoId(sidoId);
    }

    public Optional<Board> searchBoardsByTitle(String keyword) {
        return boardRepository.searchByTitle(keyword);
    }

    // Update
    public void updateBoard(Board board) {
        boardRepository.update(board);
    }

    // Delete
    public void deleteBoardById(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public void deleteBoardsByMemberId(Long memberId) {
        boardRepository.deleteByMemberId(memberId);
    }

    public void deleteAllBoards() {
        boardRepository.deleteAll();
    }

}
