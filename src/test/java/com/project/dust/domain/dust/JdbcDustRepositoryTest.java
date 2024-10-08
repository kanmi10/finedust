package com.project.dust.domain.dust;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcDustRepositoryTest {

    DustRepository repository = new JdbcDustRepository();

    @Test
    void crud() throws SQLException {
        //save
        Dust dust = new Dust();
        dust.setSidoName("서울");
        dust.setStationName("송파구");
        dust.setDataTime("2024-10-08");
        dust.setPm10Value(20);
        dust.setPm25Value(30);

        repository.save(dust);
    }

}