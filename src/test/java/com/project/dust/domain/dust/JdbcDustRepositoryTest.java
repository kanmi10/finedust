package com.project.dust.domain.dust;

import com.project.dust.dust.repository.DustRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest
class JdbcDustRepositoryTest {

    @Autowired
    private DustRepository dustRepository;


    @Test
    void save() {
    }

    @Test
    void searchDust() {
    }

    @Test
    void findStationNameByKeyword()  {
        List<String> stationNames = dustRepository.findStationNameByKeyword("중");
        System.out.println("stationNames = " + stationNames);
        Assertions.assertThat(stationNames).contains("중구").contains("중앙대로(고잔동)");
    }
}