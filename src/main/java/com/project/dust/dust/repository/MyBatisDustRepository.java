package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisDustRepository implements DustRepository{

    private final DustMapper dustMapper;

    @Override
    public void save(List<Dust> dusts) {
        dustMapper.save(dusts);
    }

    @Override
    public Dust searchDust(String stationName) {
        return dustMapper.searchDust(stationName);
    }

    @Override
    public List<String> findStationNameByKeyword(String keyword) {
        return dustMapper.findStationNameByKeyword(keyword);
    }

    @Override
    public void update(List<Dust> dusts) {
        dustMapper.update(dusts);
    }

    @Override
    public List<Map<Long, Object>> findAllSidoNames() {
        List<Map<Long, Object>> allSidoNames = dustMapper.findAllSidoNames();
        log.info("allSidoNames={}", allSidoNames);
        return dustMapper.findAllSidoNames();
    }

    @Override
    public void findAllDusts() {
        dustMapper.findAllDusts();
    }
}
