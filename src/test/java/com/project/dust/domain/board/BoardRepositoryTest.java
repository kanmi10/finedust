package com.project.dust.domain.board;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        boardRepository.deleteAll(); // 테스트 데이터 초기화
    }


    @Test
    @DisplayName("모든 게시물 조회")
    void findAll() {
        //given
        Board board1 = new Board();
        board1.setMemberId(1L);
        board1.setSidoId(1L);
        board1.setTitle("테스트 게시물입니다1.");
        board1.setContent("테스트 게시물 컨텐츠~~");

        Board board2 = new Board();
        board2.setMemberId(1L);
        board2.setSidoId(1L);
        board2.setTitle("테스트 게시물입니다2.");
        board2.setContent("테스트 게시물 컨텐츠~~");

        boardRepository.save(board1);
        boardRepository.save(board2);

        //when
        List<Board> boards = boardRepository.findAll();
        log.info("boards={}", boards);

        //then
        Assertions.assertThat(boards).hasSize(2);
        Assertions.assertThat(boards.stream().anyMatch(board -> board.getTitle().equals("테스트 게시물입니다1.")));
    }
}