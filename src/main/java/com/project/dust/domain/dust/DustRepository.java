package com.project.dust.domain.dust;

import java.sql.SQLException;
import java.util.List;

public interface DustRepository {

    void save(List<Dust> dusts) throws SQLException;

    Dust searchDust(String search) throws SQLException;

    List<String> findByStationName(String stationName) throws SQLException;

    void update(List<Dust> dusts) throws SQLException;
}
