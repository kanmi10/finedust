package com.project.dust.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

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

    public List<Board> getBoardsByMemberId(Long memberId) {
        return boardRepository.findByMemberId(memberId);
    }

    public List<Board> getBoardsBySidoId(Long sidoId) {
        return boardRepository.findBySidoId(sidoId);
    }

    public List<Board> searchBoardsByTitle(String keyword) {
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
