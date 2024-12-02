package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DustMapper {

    void save(List<Dust> dusts);

    Dust searchDust(String stationName);

    List<String> findStationNameByKeyword(String keyword);

    void update(List<Dust> dusts);

    List<RegionDTO> findAllSidoNames();

    void findAllDusts();
}