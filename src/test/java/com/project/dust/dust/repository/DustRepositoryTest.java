package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class DustRepositoryTest {

    @Autowired
    private DustRepository dustRepository;

    @Test
    @DisplayName("대기오염 데이터 검색")
    void searchDust() {
        String searchStation = "테스트측정소";

        Dust dust = new Dust();
        dust.setStationId(1L);
        dust.setStationName(searchStation);
        dust.setSidoName("서울");
        dust.setPm10Value(10);
        dust.setPm25Value(25);
        dust.setNo2Value(0.2);
        dust.setDataTime(LocalDateTime.now());

        dustRepository.save(dust);

        Dust findDust = dustRepository.searchDust(searchStation);

        assertThat(findDust).isEqualTo(dust);
    }

    @Test
    @DisplayName("측정소 키워드 검색")
    void findStationNameByKeyword() {
        String keyword = "테스";

        Dust dust1 = new Dust();
        dust1.setStationId(1L);
        dust1.setStationName("테스트측정소1");
        dust1.setSidoName("서울");
        dust1.setPm10Value(10);
        dust1.setPm25Value(25);
        dust1.setNo2Value(0.2);
        dust1.setDataTime(LocalDateTime.now());

        Dust dust2 = new Dust();
        dust2.setStationId(2L);
        dust2.setStationName("테스트측정소2");
        dust2.setSidoName("경기");
        dust2.setPm10Value(30);
        dust2.setPm25Value(10);
        dust2.setNo2Value(0.3);
        dust2.setDataTime(LocalDateTime.now());

        dustRepository.save(dust1);
        dustRepository.save(dust2);

        List<String> stationNameByKeyword = dustRepository.findStationNameByKeyword(keyword);

        assertThat(stationNameByKeyword).hasSize(2);
        assertThat(stationNameByKeyword).contains(dust1.getStationName(), dust2.getStationName());
    }

    @Test
    @DisplayName("대기오염 데이터 update")
    void foreachUpdate() {
        Dust dust = new Dust();
        dust.setStationId(1L);
        dust.setStationName("테스트측정소1");
        dust.setSidoName("서울");
        dust.setPm10Value(10);
        dust.setPm25Value(25);
        dust.setNo2Value(0.2);
        dust.setDataTime(LocalDateTime.now());

        Dust updateDust = new Dust();
        updateDust.setStationId(1L);
        updateDust.setStationName("수정된측정소");
        updateDust.setSidoName("서울");
        updateDust.setPm10Value(30);
        updateDust.setPm25Value(35);
        updateDust.setNo2Value(1.3);
        updateDust.setDataTime(LocalDateTime.now());

        List<Dust> dusts = List.of(updateDust);

        dustRepository.save(updateDust);
        dustRepository.foreachUpdate(dusts);

        Dust findDust = dustRepository.getDustById(dust.getStationId());
        assertThat(updateDust).isEqualTo(findDust);
    }

    @Test
    @DisplayName("모든 지역명 검색")
    void findAllSidoNames() {
        List<RegionDTO> allSidoNames = dustRepository.findAllSidoNames();

        RegionDTO region = new RegionDTO();
        region.setSidoId(1L);
        region.setSidoName("서울");

        assertThat(allSidoNames).contains(region);
    }

    @Test
    @DisplayName("지역ID 조회")
    void getSidoId() {
        Long sidoId = dustRepository.getSidoId("서울");
        assertThat(sidoId).isEqualTo(1);
    }
}