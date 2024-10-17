package com.project.dust.domain.dust;

import com.project.dust.connection.DBConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class DustRepositoryTest {

    DustRepository repository = new JdbcDustRepository();

    @Test
    void crud() throws SQLException {
        //save
        Dust dust = new Dust();
        dust.setSidoName("서울");
        dust.setStationName("송파구");
//        dust.setDataTime("2024-10-08");
        dust.setPm10Value(20);
        dust.setPm25Value(30);

    }

   /* @Test
    void addRegion() throws SQLException {
        JdbcDustRepository dustRepository = new JdbcDustRepository();
        dustRepository.addRegion();
    }

    @Test
    void selectRegion() {
        JdbcDustRepository dustRepository = new JdbcDustRepository();
        dustRepository.selectRegion();
    }*/

    @Test
    void trash() {
        //2024-10-16 15:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date = "2024-10-16 15:00";

        LocalDateTime parsed = LocalDateTime.parse(date, formatter);
        System.out.println("parsed = " + parsed);

    }

}