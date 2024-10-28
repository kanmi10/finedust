package com.project.dust.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into MEMBER (loginId, password, name) values (?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, member.getLoginId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("save", sql, e);
        } finally {
            close(con, pstmt, null);
        }

        return member;
    }

    @Override
    public Member findById(Long id) {
        String sql = "select * from MEMBER where memberId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, id);
            rs =  pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("memberId"));
                member.setLoginId(rs.getString("loginId"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));

                return member;
            }
            return null;

        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("findById", sql, e);
        } finally {
            close(con, pstmt, rs);
        }

    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from MEMBER";
        List<Member> members = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();;
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("memberId"));
                member.setLoginId(rs.getString("loginId"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));

                members.add(member);
            }

            return members;

        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("findAll", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public void delete(Long memberId) {
        String sql = "delete from MEMBER where memberId = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();;
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw exTranslator.translate("delete", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public void addFavorite(Long stationId, Long memberId) {
        String sql = "insert into BOOKMARK (stationId, memberId) values (?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, stationId);
            pstmt.setLong(2, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("favoriteStation", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    @Override
    public void removeFavorite(Long bookmarkId) {
        String sql = "delete from BOOKMARK where bookmarkId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, bookmarkId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("removeFavorite", sql, e);
        } finally {
            close(con, pstmt, null);
        }

    }

    @Override
    public Long getStationName(String stationName) {
        String sql = "select stationId from DUST where stationName = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, stationName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("stationId");
            }

            return null;

        } catch (SQLException e) {
            throw exTranslator.translate("getStationName", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public Long hasFavoriteForStation(Long memberId, Long stationId) {
        String sql = "select bookmarkId from BOOKMARK where memberId = ? and stationId = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, memberId);
            pstmt.setLong(2, stationId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("bookmarkId");
            }

            return null;

        } catch (SQLException e) {
            throw exTranslator.translate("hasFavoriteForStation", sql, e);
        } finally {
            close(con, pstmt, rs);
        }

    }

    @Override
    public Set<String> getFavorites(Long memberId) {
        String sql = "select (select stationName\n" +
                    "        from DUST\n" +
                    "        where BOOKMARK.stationId = DUST.stationId) stationName\n" +
                    "from BOOKMARK\n" +
                    "where memberId = ?";

        Set<String> favorites = new HashSet<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, memberId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                favorites.add(rs.getString("stationName"));
            }

            return favorites;

        } catch (SQLException e) {
            throw exTranslator.translate("getFavorites", sql, e);
        } finally {
            close(con, pstmt, rs);
        }

    }

    private void close(Connection con, PreparedStatement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        //log.info("get connection={}, class={}", con, con.getClass());
        return con;
    }

}
