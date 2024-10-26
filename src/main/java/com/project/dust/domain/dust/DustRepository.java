package com.project.dust.domain.dust;

import java.util.List;

public interface DustRepository {

    void save(List<Dust> dusts);

    Dust searchDust(String search);

    List<String> findByStationName(String stationName);

    void update(List<Dust> dusts);
}
