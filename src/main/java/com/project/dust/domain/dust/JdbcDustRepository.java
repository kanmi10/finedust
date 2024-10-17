package com.project.dust.domain.dust;

import com.project.dust.connection.DBConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

import static java.sql.Types.*;

@Slf4j
public class JdbcDustRepository implements DustRepository {

    @Override
    public Dust save(Dust dust) throws SQLException {

        System.out.println("JdbcDustRepository.save");

        String sql = "INSERT INTO DUST (stationId, sidoId, stationName, dataTime, pm10Value, pm25Value, no2Value) VALUES (?, ?, ?, ?, ?, ?, ?);";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, dust.getStationId());
            pstmt.setString(2, getSidoId(dust.getSidoName(), con));
            pstmt.setString(3, dust.getStationName());

            //2024-10-16 15:00
            pstmt.setObject(4, dust.getDataTime());

            pstmt.setObject(5, dust.getPm10Value(), INTEGER);
            pstmt.setObject(6, dust.getPm25Value(), INTEGER);
            pstmt.setObject(7, dust.getNo2Value(), DOUBLE);
            pstmt.executeUpdate();
            return dust;
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