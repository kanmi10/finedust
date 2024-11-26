package com.project.dust.board.service;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;
import com.project.dust.board.Page;
import com.project.dust.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Page<Board> searchBoards(Long sidoId, Integer typeNumber, String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        BoardSearchDTO boardSearchDTO = new BoardSearchDTO(sidoId, typeNumber, keyword, pageSize, offset);

        List<Board> boards = boardRepository.findBoards(boardSearchDTO);

        int totalCount = boardRepository.countAllBoards(boardSearchDTO);

        if (boards == null || boards.isEmpty()) {
            log.info("검색된 페이지가 없습니다");
            return new Page<>(boards, 0, 0, 0, sidoId);
        }

        return new Page<>(boards, page, pageSize, totalCount, sidoId);
    }

    // Update
    public void updateBoard(Board board) {
        boardRepository.update(board);
    }

    // Delete
    public void deleteBoardById(Long boardId) {
        boardRepository.deleteById(boardId);
    }


    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow();
    }

    public String getSidoName(Long sidoId) {
        return boardRepository.getSidoName(sidoId);
    }
}
