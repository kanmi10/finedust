package com.project.dust.domain.dust;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DustService {

    private static List<Dust> dusts = new ArrayList<>();
    private static long sequence = 0L;
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
        JSONArray items = (JSONArray) body.get("items");

        /**
         *     private Long stationId;         //측정소 id
         *     private String sidoName;        //시도명
         *     private String stationName;     //측정소명
         *     private LocalDateTime dataTime; //측정일시
         *     private Integer pm10Value;      //미세먼지(PM10 농도)
         *     private Integer pm25Value;      //초미세먼지(PM25 농도)
         *     private Integer no2Value;       //이산화질소 농도
         */
        for (int i = 0; i < items.size(); i++) {
            String sidoName = (String) ((JSONObject) items.get(i)).get("sidoName");
            String stationName = (String) ((JSONObject) items.get(i)).get("stationName");
            String dataTime = (String) ((JSONObject) items.get(i)).get("dataTime");

            Integer pm10Value = getPmValue(items, i, "pm10Value");
            Integer pm25Value = getPmValue(items, i, "pm25Value");
            Double no2Value = getNo2Value(items, i, "no2Value");

            Dust dust = new Dust();
            dust.setStationId(++sequence);
            dust.setSidoName(sidoName);
            dust.setStationName(stationName);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dust.setDataTime(getDateTime(dataTime, formatter));
            dust.setPm10Value(pm10Value);
            dust.setPm25Value(pm25Value);
            dust.setNo2Value(no2Value);

            dusts.add(dust);
        }

        //dustRepository.save(dusts);
    }

    public Dust searchDust(String search) throws SQLException {
        return dustRepository.searchDust(search);
    }

    private LocalDateTime getDateTime(String dataTime, DateTimeFormatter formatter) {
        try {
            return LocalDateTime.parse(dataTime, formatter);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private Integer getPmValue(JSONArray itemsArr, int i, String pm) {
        try {
            return Integer.valueOf((String) ((JSONObject) itemsArr.get(i)).get(pm));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double getNo2Value(JSONArray itemsArr, int i, String ppm) {
        try {
            return Double.parseDouble((String)((JSONObject) itemsArr.get(i)).get(ppm));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

}
