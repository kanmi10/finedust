package com.project.dust.domain.board;

import com.project.dust.board.Board;
import com.project.dust.board.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


@Slf4j
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BoardRepository boardRepository;


//    @BeforeEach
//    void setUp() {
//        boardRepository.deleteAll(); // 테스트 데이터 초기화
//    }


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
//        List<Board> boards = boardRepository.findBoards();
//        log.info("boards={}", boards);

//        //then
//        Assertions.assertThat(boards).hasSize(2);
//        Assertions.assertThat(boards.stream().anyMatch(board -> board.getTitle().equals("테스트 게시물입니다1.")));
    }

    @Test
    @DisplayName("잘못된 컬럼명 테스트")
    void wrongColumn() {
        String sql = "select * from board";

        SQLExceptionTranslator exTranslator = new CustomSQLExceptionTranslator(dataSource);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                log.info("rs.getString(잘못된 컬럼명) {}", rs.getString("잘못된 컬럼명"));
            }

        } catch (SQLException e) {
            log.error("SQL State: {}, Error Code: {}, Message: {}", e.getSQLState(), e.getErrorCode(), e.getMessage());
            DataAccessException resultEx = exTranslator.translate("findAll", sql, e);
            log.info("에외 메세지={}", resultEx.getMessage());
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
            JdbcUtils.closeConnection(con);
        }
    }

    @Test
    @DisplayName("전체 게시글 개수")
    void countBoardsByKeyword() {
//        int count = boardRepository.countBoardsByKeyword("title", "한");
//        log.info("count={}", count);
    }

    class CustomSQLExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

        private final DataSource dataSource;

        CustomSQLExceptionTranslator(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        protected DataAccessException customTranslate(String task, String sql, SQLException ex) {
            if (ex.getErrorCode() == 0) { // 특정 SQL 상태 코드 처리
                return new InvalidDataAccessResourceUsageException("컬럼명이 잘못됐습니다.", ex);
            }
            return null; // 기본 처리
        }
    }

}