package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;

import java.util.List;
import java.util.Map;

public interface DustRepository {

    void save(List<Dust> dusts);

    Dust searchDust(String stationName);

    List<String> findStationNameByKeyword(String keyword);

    void update(List<Dust> dusts);

    List<Map<Long, Object>> findAllSidoNames();

    void findAllDusts();
}
