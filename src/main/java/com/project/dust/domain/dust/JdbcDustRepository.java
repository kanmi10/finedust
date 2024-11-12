package com.project.dust.domain.dust;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.*;

@Slf4j
@Primary
@Repository
public class JdbcDustRepository implements DustRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exTranslator;

    public JdbcDustRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Override
    public void save(List<Dust> dusts) {

        String sql = "INSERT INTO DUST (stationId, sidoId, stationName, dataTime, pm10Value, pm25Value, no2Value) VALUES (?, ?, ?, ?, ?, ?, ?);";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
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
            throw exTranslator.translate("save", sql, e);
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
            throw exTranslator.translate("getSidoId", sql, e);
        } finally {
            close(null, pstmt, rs);
        }

        return null;
    }

    @Override
    public Dust searchDust(String search) {
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
            con = getConnection();
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
            throw exTranslator.translate("searchDust", sql, e);
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public List<String> findByStationName(String stationName) {
        String sql = "select stationName\n" +
                     "from DUST\n" +
                     "where stationName like ?";

        List<String> stationsNames = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
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
            throw exTranslator.translate("findByStationName", sql, e);
        } finally {
            close(con, pstmt, rs);
        }

    }

    @Override
    public void update(List<Dust> dusts) {
        String sql = "update DUST\n" +
                    "set dataTime = ?, pm10Value = ?, pm25Value = ?, no2Value = ?\n" +
                    "where stationName = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
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
            throw exTranslator.translate("update", sql, e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private void close(Connection con, PreparedStatement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }



}