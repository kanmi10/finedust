package com.project.dust.domain.dust;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryDustRepository implements DustRepository {

    public static final List<Dust> dustDTOArr = new ArrayList<>();
    private static Long sequence = 0L;

    @Override
    public Dust save(Dust dust) {
        dust.setStationId(++sequence);
        dustDTOArr.add(dust);
        return dust;
    }


}
