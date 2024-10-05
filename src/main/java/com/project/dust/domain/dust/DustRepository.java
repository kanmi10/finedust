package com.project.dust.domain.dust;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DustRepository {

    public static final List<Dust> dustDTOArr = new ArrayList<>();

    public Dust save(Dust dust) {
        dustDTOArr.add(dust);
        return dust;
    }

    public void clear() {
        dustDTOArr.clear();
    }

    public List<Dust> show() {
        return dustDTOArr;
    }

}
