package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisDustRepository implements DustRepository{

    private final DustMapper dustMapper;

    @Override
    public void save(Dust dust) {
        dustMapper.save(dust);
    }

    @Override
    public Dust searchDust(String stationName) {
        return dustMapper.searchDust(stationName);
    }

    @Override
    public Dust getDustById(Long stationId) {
        return dustMapper.getDustById(stationId);
    }

    @Override
    public List<String> findStationNameByKeyword(String keyword) {
        return dustMapper.findStationNameByKeyword(keyword);
    }

    @Override
    public void foreachUpdate(List<Dust> dusts) {
        dustMapper.foreachUpdate(dusts);
    }

    @Override
    public void loopUpdate(Dust dust) {
        dustMapper.loopUpdate(dust);
    }

    @Override
    public List<RegionDTO> findAllSidoNames() {
        return dustMapper.findAllSidoNames();
    }


    @Override
    public Long getSidoId(String sidoName) {
        return dustMapper.getSidoId(sidoName);
    }

    @Override
    public Long getStationId(String stationName) {
        return dustMapper.getStationId(stationName);
    }
}
