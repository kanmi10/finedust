package com.project.dust.dust.service;

import com.project.dust.dust.Dust;
import com.project.dust.dust.repository.DustRepository;
import com.project.dust.dust.repository.RegionDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class DustService {

    private static final List<Dust> dusts = new ArrayList<>();
    private static long sequence = 0L;
    private final DustRepository dustRepository;

    public DustService(DustRepository dustRepository) {
        this.dustRepository = dustRepository;
    }

    // 같은 connection 사용해야 함.
    public Dust searchDust(String search) {
        //1. 측정소명으로 대기오염 데이터를 조회하고 dust 객체에 담음
        Dust dust = dustRepository.searchDust(search);
        log.info("DustService.dust={}", dust);

        // 검색된 결과 없음
        if (dust == null) {
            throw new NoSuchElementException("검색된 결과가 없습니다.");
        }

        //2. dust 객체에 담겨있는 측정일시와 사용자 데이터 요청일시를 비교
        LocalDateTime requestTime = LocalDateTime.now();
        log.info("requestTime={}", requestTime);
        LocalDateTime dataTime = dust.getDataTime();
        log.info("dataTime={}", dataTime);

        if (isDataOutOfDate(dataTime, requestTime)) {
            //3-1. 만약 오래된 데이터일 경우, API에서 데이터를 다시 가져와 DB 업데이트 한후 다시 대기오염 데이터를 조회해 dust 객체에 담음
            log.info("오래된 데이터! 데이터 업데이트를 시작합니다.");
            update(dust.getSidoName());
            return dustRepository.searchDust(search);
        }

        //3-2. 최신 데이터일 경우 1에서 조회한 데이터를 사용자에게 보여줌
        return dust;
    }

    // 데이터가 오래 됐는지
    private boolean isDataOutOfDate(LocalDateTime dataTime, LocalDateTime requestTime) {
        Duration between = Duration.between(dataTime, requestTime);
        log.info("요청 시간 - 측정 시간 = {}", between.toMinutes());
        return between.toMinutes() >= 60;
    }

    private void update(String sidoName) {
        String jsonData = dustInit(sidoName);
        apiJsonParser(jsonData);

        log.info("API 가져온 데이터={}", dusts);
        log.info("API 데이터 개수={}", dusts.size());

        dustRepository.update(dusts);
    }


    public void init(String sidoName)  {
        //API 요청
        String apiJsonData = dustInit(sidoName);

        //API 반환 JSON 파싱
        apiJsonParser(apiJsonData);

        //DB 저장
        dustRepository.save(dusts);
    }

    private void apiJsonParser(String apiJsonData) {
        //JSON 파서 객체 생성
        JSONParser parser = new JSONParser();
        //JSON 객체 생성
        JSONObject object;
        try {
            object = (JSONObject) parser.parse(apiJsonData);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
    }

    /**
     * API 요청 URL 제작
     * @return API 요청 URL
     */
    private String getApiUrl(String sidoName) {
        /**
         * 서비스키	        serviceKey	4	필수	-	공공데이터포털에서 받은 인증키
         * 데이터표출방식	    returnType	4	옵션	xml	xml 또는 json
         * 한 페이지 결과 수	numOfRows	4	옵션	100	한 페이지 결과 수
         * 페이지 번호	        pageNo	    4	옵션	1	페이지번호
         * 시도명	            sidoName	10	필수	서울	시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
         * 오퍼레이션 버전	    ver	        4	옵션	1.0	버전별 상세 결과 참고
         */
        /*URL*/
        return "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty" + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=VvAbEIzs7k39WC7z6EbjgcZyXFWCBcR5Da8Kugfd1OXp1WRG7LieBQqDHSIaRgFOHEE62zzCgLqST%2F22LMwHww%3D%3D" + /*Service Key*/
                "&" + URLEncoder.encode("returnType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8) + /*xml 또는 json*/
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1000", StandardCharsets.UTF_8) + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) + /*페이지번호*/
                "&" + URLEncoder.encode("sidoName", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(sidoName, StandardCharsets.UTF_8) + /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
                "&" + URLEncoder.encode("ver", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1.0", StandardCharsets.UTF_8);
    }

    public String dustInit(String sidoName) {
        // 조합완료된 요청 URL 생성 / HttpURLConnection 객체 활용 API 요청
        HttpURLConnection conn = null;
        BufferedReader rd = null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(getApiUrl(sidoName));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            // 요청 결과 출력을 위한 준비

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            // 시스템 자원 반납 및 연결해제
            rd.close();
            conn.disconnect();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
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
        } catch (NumberFormatException | NullPointerException e) {
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

    public List<String> findStationsByName(String stationName) {
        return dustRepository.findStationNameByKeyword(stationName);
    }

    public List<RegionDTO> fetchSidoNames() {
        return dustRepository.findAllSidoNames();
    }

}
