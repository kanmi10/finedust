package com.project.dust.domain.dust;

import com.project.dust.connection.ConnectionConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcDustRepositoryTest {

    DustRepository dustRepository = new JdbcDustRepository(new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD));

    @Test
    void save() {
    }

    @Test
    void searchDust() {
    }

    @Test
    void findByStationName() throws SQLException {
        List<String> stationNames = dustRepository.findByStationName("중");
        System.out.println("stationNames = " + stationNames);
        Assertions.assertThat(stationNames).contains("중구").contains("중앙대로(고잔동)");
    }
}