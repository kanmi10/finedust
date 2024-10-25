package com.project.dust.domain.dust;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class MemoryDustRepository implements DustRepository {

    @Override
    public void save(List<Dust> dusts) {
        log.info("dusts={}", dusts);
    }

    @Override
    public Dust searchDust(String search) {
        return null;
    }

    @Override
    public List<String> findByStationName(String stationName) {
        return List.of();
    }

    @Override
    public void update(List<Dust> dusts) {

    }

}
