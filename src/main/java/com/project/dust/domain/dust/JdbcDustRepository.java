package com.project.dust.domain.dust;

import com.project.dust.connection.DBConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JdbcDustRepository implements DustRepository {

    @Override
    public Dust save(Dust dust) throws SQLException {
        String sql = "INSERT INTO dust (sido_name, station_name, data_time, pm10_value, pm25_value) VALUES (?, ?, ?, ?, ?);";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dust.getSidoName());
            pstmt.setString(2, dust.getStationName());
            pstmt.setString(3, dust.getDataTime());
            pstmt.setObject(4, dust.getPm10Value(), java.sql.Types.INTEGER);
            pstmt.setObject(5, dust.getPm25Value(), java.sql.Types.INTEGER);
            pstmt.executeUpdate();
            log.info("dust={}", dust);
            return dust;
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