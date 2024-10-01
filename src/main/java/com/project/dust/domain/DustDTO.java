package com.project.dust.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DustDTO {

    private String sidoName;    //시도명
    private String stationName; //측정소명
    private String dataTime;    //측정일시
    private Integer pm10Value;  //미세먼지(PM10 농도)
    private Integer pm25Value;  //미세먼지(PM25 농도)


}
