package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class MemoryDustRepository {

//    @Override
    public void save(List<Dust> dusts) {
        log.info("dusts={}", dusts);
    }

//    @Override
    public Dust searchDust(String stationName) {
        return null;
    }

//    @Override
    public List<String> findStationNameByKeyword(String keyword) {
        return List.of();
    }

//    @Override
    public void update(List<Dust> dusts) {

    }

//    @Override
    public Map<Long, String> findAllSidoNames() {
        return Map.of();
    }

//    @Override
    public void findAllDusts() {

    }


}
