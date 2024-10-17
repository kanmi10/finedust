package com.project.dust.domain.dust;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryDustRepository implements DustRepository {

    public static final List<Dust> dustDTOArr = new ArrayList<>();
    private static Long sequence = 0L;

    public Dust save(Dust dust) {
        dust.setStationId(++sequence);
        dustDTOArr.add(dust);
        return dust;
    }


    @Override
    public void save(List<Dust> dusts) throws SQLException {

    }

    @Override
    public Dust searchDust(String search) throws SQLException {
        return null;
    }
}
