package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;

import java.util.List;
import java.util.Map;

public interface DustRepository {

    void save(List<Dust> dusts);

    Dust searchDust(String search);

    List<String> findByStationName(String stationName);

    void update(List<Dust> dusts);

    Map<Long, String> findAllSidoNames();
}
