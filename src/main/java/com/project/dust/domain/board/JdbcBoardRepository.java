package com.project.dust.domain.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcBoardRepository implements BoardRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public JdbcBoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public void save(Board board) {
        String sql = "insert into BOARD (sidoId, memberId, title, content) values (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, board.getSidoId());
            pstmt.setLong(2, board.getMemberId());
            pstmt.setString(3, board.getTitle());
            pstmt.setString(4, board.getContent());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw exTranslator.translate("save", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public Optional<Board> findById(Long boardId) {
        return findAll().stream()
                .filter(board -> board.getBoardId().equals(boardId))
                .findAny();
    }

    @Override
    public List<Board> findAll() {
        String sql = "select boardId,\n" +
                "       sidoId,\n" +
                "       (select sidoName\n" +
                "        from REGION\n" +
                "        where sidoId = BOARD.sidoId) sidoName,\n" +
                "        memberId,\n" +
                "        (select name\n" +
                "         from MEMBER\n" +
                "         where BOARD.memberId = MEMBER.memberId) name,\n" +
                "    title,\n" +
                "    content,\n" +
                "    created_date\n" +
                "from BOARD\n" +
                "order by boardId desc";

        List<Board> boards = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                Board board = new Board();
                board.setBoardId(rs.getLong("boardId"));
                board.setSidoId(rs.getLong("sidoId"));
                board.setSidoName(rs.getString("sidoName"));
                board.setMemberId(rs.getLong("memberId"));
                board.setName(rs.getString("name"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));

                Timestamp createdTime = rs.getTimestamp("created_date");
                board.setCreated_date(createdTime.toLocalDateTime());

                boards.add(board);
            }

        } catch (SQLException e) {
            throw exTranslator.translate("findAll", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
        return boards;
    }


    // limit: 한 페이지 당 게시물 수
    // offset: 몇 번째 행부터 출력할 지
    @Override
    public List<Board> findBoards(int limit, int offset) {
        String sql = "select boardId,\n" +
                "       sidoId,\n" +
                "       (select sidoName\n" +
                "        from REGION\n" +
                "        where sidoId = BOARD.sidoId) sidoName,\n" +
                "        memberId,\n" +
                "        (select name\n" +
                "         from MEMBER\n" +
                "         where BOARD.memberId = MEMBER.memberId) name,\n" +
                "    title,\n" +
                "    content,\n" +
                "    created_date\n" +
                "from BOARD\n" +
                "order by boardId desc\n " +
                "LIMIT ? OFFSET ?";

        List<Board> boards = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Board board = new Board();
                board.setBoardId(rs.getLong("boardId"));
                board.setSidoId(rs.getLong("sidoId"));
                board.setSidoName(rs.getString("sidoName"));
                board.setMemberId(rs.getLong("memberId"));
                board.setName(rs.getString("name"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));

                Timestamp createdTime = rs.getTimestamp("created_date");
                board.setCreated_date(createdTime.toLocalDateTime());

                boards.add(board);
            }

            return boards;

        } catch (SQLException e) {
            throw exTranslator.translate("findAll", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public int countBoards() {
        String sql = "SELECT COUNT(*) FROM BOARD";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw exTranslator.translate("countBoards", sql, e);
        } finally {
            close(con, pstmt, null);
        }

        return 0;
    }

    @Override
    public Optional<Board> findByMemberId(Long memberId) {
        return findAll().stream()
                .filter(board -> board.getMemberId().equals(memberId))
                .findAny();
    }

    @Override
    public Optional<Board> findBySidoId(Long sidoId) {
        return findAll().stream()
                .filter(board -> board.getSidoId().equals(sidoId))
                .findAny();
    }

    @Override
    public Optional<Board> searchByTitle(String keyword) {
        return findAll().stream()
                .filter(board -> board.getTitle().equals(keyword))
                .findAny();
    }

    @Override
    public void update(Board board) {
        String sql = "update BOARD\n" +
                "set sidoId = ?, title = ?, content=?\n" +
                "where boardId = ? and memberId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, board.getSidoId());
            pstmt.setString(2, board.getTitle());
            pstmt.setString(3, board.getContent());
            pstmt.setLong(4, board.getBoardId());
            pstmt.setLong(5, board.getMemberId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw exTranslator.translate("update", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public void deleteById(Long boardId) {
        String sql = "delete from BOARD where boardId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, boardId);
            pstmt.executeUpdate();
            log.info("게시글 삭제 완료");
        } catch (SQLException e) {
            throw exTranslator.translate("delete", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public void deleteByMemberId(Long memberId) {

    }

    @Override
    public void deleteAll() {
        String sql = "delete from BOARD";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("deleteAll", sql, e);
        } finally {
            close(con, pstmt, null);
        }

    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

}
