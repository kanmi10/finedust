package com.project.dust.domain.dust;

import com.project.dust.connection.DBConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        String sql = "select sidoId from region where sidoName = ?";
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
            close(null, pstmt, rs);
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
                dust.setNo2Value((Double) rs.getObject("no2Value"));

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

    @Override
    public List<String> findByStationName(String stationName) throws SQLException {
        String sql = "select stationName\n" +
                     "from DUST\n" +
                     "where stationName like ?";

        List<String> stationsNames = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, stationName + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                stationsNames.add(rs.getString("stationName"));
            }

//            log.info("stations={}", stationsNames);

            return stationsNames;

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }

    }

    @Override
    public void update(List<Dust> dusts) throws SQLException {
        String sql = "update DUST\n" +
                    "set dataTime = ?, pm10Value = ?, pm25Value = ?, no2Value = ?\n" +
                    "where stationName = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);

            for (Dust dust : dusts) {
                pstmt.setObject(1, dust.getDataTime());
                pstmt.setObject(2, dust.getPm10Value(), INTEGER);
                pstmt.setObject(3, dust.getPm25Value(), INTEGER);
                pstmt.setObject(4, dust.getNo2Value(), DOUBLE);
                pstmt.setString(5, dust.getStationName());
                pstmt.executeUpdate();
                log.info("dust={}", dust);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
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