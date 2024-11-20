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

    // offset = (사용자가 요청한 페이지 수 - 1) * (한 페이지당 게시물 수)
    // 사용자가 3페이지를 요청: offset = (3 - 1) * 10 = 20
    public Page<Board> getBoards(int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        // 한 페이지 당 게시물 수와 계산한 offset을 인수로 넘김
        List<Board> boards = boardRepository.findBoards(pageSize, offset);

        // 총 게시물 수
        int totalCount = boardRepository.countBoards();

        return new Page<>(boards, page, pageSize, totalCount);
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

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow();
    }
}
