package com.project.dust.board.repository;

import com.project.dust.board.Board;
import com.project.dust.board.BoardSearchDTO;
import com.project.dust.member.Member;
import com.project.dust.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@Transactional
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void before() {
        member = new Member("test", "테스트", "");
        memberRepository.save(member);
    }

    @AfterEach
    void after() {
        memberRepository.deleteById(1L);
    }

    @Test
    @DisplayName("모든 게시물 조회")
    void findAllBoards() {
        //given
        Board board1 = new Board();
        board1.setMemberId(member.getId());
        board1.setSidoId(1L);
        board1.setTitle("테스트 게시물입니다1.");
        board1.setContent("테스트 게시물 컨텐츠~~");

        Board board2 = new Board();
        board2.setMemberId(member.getId());
        board2.setSidoId(1L);
        board2.setTitle("테스트 게시물입니다2.");
        board2.setContent("테스트 게시물 컨텐츠~~");

        //when
        boardRepository.save(board1);
        boardRepository.save(board2);

        BoardSearchDTO searchDTO = new BoardSearchDTO(null, null, null, 10, 0);

        //then
        List<Board> allBoards = boardRepository.findAllBoards(searchDTO);
        assertThat(allBoards).hasSize(2);
    }


    @Test
    @DisplayName("게시물 조회")
    void findById() {
        Board board = new Board();
        board.setMemberId(member.getId());
        board.setSidoId(1L);
        board.setTitle("테스트 게시물입니다1.");
        board.setContent("테스트 게시물 컨텐츠~~");

        boardRepository.save(board);

        Board findBoard = boardRepository.findById(board.getBoardId()).orElseGet(() -> board);

        assertThat(findBoard).isEqualTo(board);
    }


    @Test
    @DisplayName("지역명 조회")
    void getSidoName() {
        //given
        Long sidoId = 1L;

        Board board = new Board();
        board.setMemberId(member.getId());
        board.setSidoId(sidoId);
        board.setTitle("테스트 게시물입니다1.");
        board.setContent("테스트 게시물 컨텐츠~~");

        //when
        String sidoName = boardRepository.getSidoName(sidoId);

        //then
        assertThat(sidoName).isEqualTo("서울");
    }

    @Test
    @DisplayName("게시물 개수 조회")
    void countAllBoards() {
        //given
        Board board1 = new Board();
        board1.setMemberId(member.getId());
        board1.setSidoId(1L);
        board1.setTitle("테스트 게시물입니다1.");
        board1.setContent("테스트 게시물 컨텐츠~~ 444");

        Board board2 = new Board();
        board2.setMemberId(member.getId());
        board2.setSidoId(1L);
        board2.setTitle("테스트 게시물입니다2.");
        board2.setContent("테스트 게시물 컨텐츠~~ 333");

        Board board3 = new Board();
        board3.setMemberId(member.getId());
        board3.setSidoId(7L);
        board3.setTitle("테스트 게시물입니다3.");
        board3.setContent("테스트 게시물 컨텐츠~~ 222");

        Board board4 = new Board();
        board4.setMemberId(member.getId());
        board4.setSidoId(8L);
        board4.setTitle("테스트 게시물입니다4.");
        board4.setContent("테스트 게시물 컨텐츠~~ 111");

        //when
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);

        //전체 게시물 개수
        BoardSearchDTO searchAll = new BoardSearchDTO(null, null, null, 10, 0);
        int countAllBoards = boardRepository.countAllBoards(searchAll);
        assertThat(countAllBoards).isEqualTo(4);

        //지역 게시물 조회(서울)
        BoardSearchDTO searchRegion = new BoardSearchDTO(1L, null, null, 10, 0);
        int countRegion = boardRepository.countAllBoards(searchRegion);
        assertThat(countRegion).isEqualTo(2);

        //검색타입 조회(1:제목)
        BoardSearchDTO searchTitle = new BoardSearchDTO(null, 1, "테스", 10, 0);
        int countTitle = boardRepository.countAllBoards(searchTitle);
        assertThat(countTitle).isEqualTo(4);

        //검색타입 조회(2:내용)
        BoardSearchDTO searchContent = new BoardSearchDTO(null, 2, "444", 10, 0);
        int countContent = boardRepository.countAllBoards(searchContent);
        assertThat(countContent).isEqualTo(1);

        //검색타입 조회(3:이름)
        BoardSearchDTO searchName = new BoardSearchDTO(null, 3, "테스트", 10, 0);
        int countName = boardRepository.countAllBoards(searchName);
        assertThat(countName).isEqualTo(4);

        //검색타입 조회(4:제목+이름)
        BoardSearchDTO searchTitleAndContent = new BoardSearchDTO(null, 4, "1", 10, 0);
        int countTitleAndContent = boardRepository.countAllBoards(searchTitleAndContent);
        assertThat(countTitleAndContent).isEqualTo(2);
    }

    @Test
    @DisplayName("게시물 수정")
    void update() {
        Board board = new Board();
        board.setMemberId(member.getId());
        board.setSidoId(1L);
        board.setTitle("테스트 게시물입니다.");
        board.setContent("테스트 게시물 컨텐츠~~ ");

        boardRepository.save(board);

        String updateTitle = "수정 제목";
        String updateContent = "수정 내용";
        Long updateSidoId = 3L;

        Board updateBoard = new Board();
        updateBoard.setMemberId(board.getMemberId());
        updateBoard.setBoardId(board.getBoardId());
        updateBoard.setSidoId(updateSidoId);
        updateBoard.setTitle(updateTitle);
        updateBoard.setContent(updateContent);

        boardRepository.update(updateBoard);

        Board findBoard = boardRepository.findById(updateBoard.getBoardId()).get();

        assertThat(findBoard.getTitle()).isEqualTo(updateTitle);
        assertThat(findBoard.getContent()).isEqualTo(updateContent);
        assertThat(findBoard.getSidoId()).isEqualTo(updateSidoId);
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteById() {
        Board board = new Board();
        board.setMemberId(member.getId());
        board.setSidoId(1L);
        board.setTitle("테스트 게시물입니다.");
        board.setContent("테스트 게시물 컨텐츠~~ ");

        boardRepository.save(board);
        boardRepository.deleteById(board.getBoardId());

        Optional<Board> findBoard = boardRepository.findById(board.getBoardId());

        assertThat(findBoard).isEmpty();
    }
}