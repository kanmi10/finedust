package com.project.dust.domain.dust;

import java.sql.SQLException;
import java.util.List;

public interface DustRepository {

    void save(List<Dust> dusts) throws SQLException;



}
