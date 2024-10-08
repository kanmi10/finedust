package com.project.dust.domain.dust;

import lombok.Getter;
import lombok.Setter;

/**
 * CREATE TABLE dust (
 *     id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 시퀀스 (자동 증가)
 *     sido_name VARCHAR(100) NOT NULL,       -- 시도명
 *     station_name VARCHAR(100) NOT NULL,    -- 측정소명
 *     data_time VARCHAR(50) NOT NULL,        -- 측정일시
 *     pm10_value INT,                        -- 미세먼지(PM10 농도)
 *     pm25_value INT                         -- 미세먼지(PM25 농도)
 * );
 */
@Getter @Setter
public class Dust {

    private String sidoName;    //시도명
    private String stationName; //측정소명
    private String dataTime;    //측정일시
    private Integer pm10Value;  //미세먼지(PM10 농도)
    private Integer pm25Value;  //미세먼지(PM25 농도)

}
