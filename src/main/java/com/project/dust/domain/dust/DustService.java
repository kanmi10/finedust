package com.project.dust.domain.dust;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class DustService {

    private final DustRepository dustRepository;

    public DustService(DustRepository dustRepository) {
        this.dustRepository = new JdbcDustRepository();
    }

    public void init(String jsonData) throws ParseException, SQLException {
        //JSON 파서 객체 생성
        JSONParser parser = new JSONParser();

        //JSON 객체 생성
        JSONObject object = (JSONObject) parser.parse(jsonData);
        JSONObject response = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONArray itemsArr = (JSONArray) body.get("items");

        /**
         *     private String sidoName;    //시도명
         *     private String stationName; //측정소명
         *     private String dataTime;    //측정일시
         *     private Integer pm10Value;  //미세먼지(PM10 농도)
         *     private Integer pm25Value;  //미세먼지(PM25 농도)
         */
        for (int i = 0; i < itemsArr.size(); i++) {
            String sidoName = (String) ((JSONObject) itemsArr.get(i)).get("sidoName");
            String stationName = (String) ((JSONObject) itemsArr.get(i)).get("stationName");
            String dataTime = (String) ((JSONObject) itemsArr.get(i)).get("dataTime");

            Integer pm10Value = getPmValue(itemsArr, i, "pm10Value");
            Integer pm25Value = getPmValue(itemsArr, i, "pm25Value");

            Dust dust = new Dust();
            dust.setSidoName(sidoName);
            dust.setStationName(stationName);
            dust.setDataTime(dataTime);
            dust.setPm10Value(pm10Value);
            dust.setPm25Value(pm25Value);

            dustRepository.save(dust);
        }

        log.info("object={}", object);
        log.info("response={}", response);
        log.info("body={}", body);
        log.info("items={}", itemsArr);
    }

    private static Integer getPmValue(JSONArray itemsArr, int i, String pm) {
        try {
            return Integer.valueOf((String) ((JSONObject) itemsArr.get(i)).get(pm));
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
