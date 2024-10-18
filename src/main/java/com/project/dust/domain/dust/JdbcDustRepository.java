package com.project.dust.domain.dust;

import com.project.dust.connection.DBConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.sql.Types.*;

@Slf4j
public class JdbcDustRepository implements DustRepository {

    @Override
    public void save(List<Dust> dusts) throws SQLException {

        String sql = "INSERT INTO DUST (stationId, sidoId, stationName, dataTime, pm10Value, pm25Value, no2Value) VALUES (?, ?, ?, ?, ?, ?, ?);";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);

            for (Dust dust : dusts) {
                pstmt.setLong(1, dust.getStationId());
                pstmt.setString(2, getSidoId(dust.getSidoName(), con));
                pstmt.setString(3, dust.getStationName());
                //2024-10-16 15:00
                pstmt.setObject(4, dust.getDataTime());
                pstmt.setObject(5, dust.getPm10Value(), INTEGER);
                pstmt.setObject(6, dust.getPm25Value(), INTEGER);
                pstmt.setObject(7, dust.getNo2Value(), DOUBLE);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }


    private String getSidoId(String sidoName, Connection con) {
        String sql = "select sidoId from region where sidoName=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, sidoName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("sidoId");
            }
        } catch (SQLException e) {
            log.error("db error", e);
        } finally {
            close(null, pstmt, null);
        }

        return null;
    }

    @Override
    public Dust searchDust(String search) throws SQLException {
        String sql = "select\n" +
                "    stationName,\n" +
                "    (select sidoName\n" +
                "     from REGION\n" +
                "     where REGION.sidoId = DUST.sidoId) sidoName,\n" +
                "    dataTime,\n" +
                "    pm10Value,\n" +
                "    pm25Value,\n" +
                "    no2Value\n" +
                "from DUST\n" +
                "where stationName = ?;";

        log.info("sql={}", sql);

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, search);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Dust dust = new Dust();
                dust.setSidoName(rs.getString("sidoName"));
                dust.setStationName(rs.getString("stationName"));
                // DataTime 타입 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parsedDataTime = LocalDateTime.parse(rs.getString("dataTime"), formatter);
                dust.setDataTime(parsedDataTime);
                dust.setPm10Value((Integer) rs.getObject("pm10Value"));
                dust.setPm25Value((Integer) rs.getObject("pm25Value"));
                dust.setNo2Value(rs.getDouble("no2Value"));

                /*log.info("rs.getInt(pm10Value)={}", rs.getObject("pm10Value"));
                log.info("rs.getInt(pm25Value)={}", rs.getObject("pm25Value"));
                log.info("rs.getInt(no2Value)={}", rs.getObject("no2Value"));
                log.info("repository.dust={}", dust);*/

                return dust;
            }
            return null;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void addRegion() throws SQLException {
        String sql = "insert into REGION (sidoId, sidoName) values (?, ?);";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            log.info("con={}", con);
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setString(2, "서울");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    void selectRegion() {
        String sql = "select * from REGION;";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, pstmt, rs);
        }
    }


    private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
}