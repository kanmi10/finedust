package com.project.dust.dust.repository;

import com.project.dust.dust.Dust;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DustMapper {

    void save(Dust dust);

    Dust searchDust(String stationName);

    Dust getDustById(Long stationId);

    List<String> findStationNameByKeyword(String keyword);

    void foreachUpdate(List<Dust> dusts);

    void loopUpdate(Dust dust);

    List<RegionDTO> findAllSidoNames();

    Long getSidoId(String sidoName);

    Long getStationId(String stationName);
}
