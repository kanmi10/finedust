package com.project.dust.web.controller;

import com.project.dust.domain.DustDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class DustController {

    /**
     * 서비스키	serviceKey	4	필수	-	공공데이터포털에서 받은 인증키
     * 데이터표출방식	returnType	4	옵션	xml	xml 또는 json
     * 한 페이지 결과 수	numOfRows	4	옵션	100	한 페이지 결과 수
     * 페이지 번호	pageNo	4	옵션	1	페이지번호
     * 시도명	sidoName	10	필수	서울	시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
     * 오퍼레이션 버전	ver	4	옵션	1.0	버전별 상세 결과 참고
     */

    @GetMapping("/open-api")
    public List<DustDTO> openApi() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=VvAbEIzs7k39WC7z6EbjgcZyXFWCBcR5Da8Kugfd1OXp1WRG7LieBQqDHSIaRgFOHEE62zzCgLqST%2F22LMwHww%3D%3D"); /*Service Key*/
        urlBuilder.append("&").append(URLEncoder.encode("returnType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("json", StandardCharsets.UTF_8)); /*xml 또는 json*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("100", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("sidoName", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("서울", StandardCharsets.UTF_8)); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&").append(URLEncoder.encode("ver", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1.0", StandardCharsets.UTF_8)); /*버전별 상세 결과 참고*/

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());
        JSONObject response = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONArray itemsArr = (JSONArray) body.get("items");

        log.info("size={}", itemsArr.size());

        ArrayList<DustDTO> arrayList = new ArrayList<>();
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
            Integer pm10Value = Integer.valueOf((String)((JSONObject) itemsArr.get(i)).get("pm10Value"));
            Integer pm25Value = Integer.valueOf((String)((JSONObject) itemsArr.get(i)).get("pm25Value"));


            DustDTO dustDTO = new DustDTO();
            dustDTO.setSidoName(sidoName);
            dustDTO.setStationName(stationName);
            dustDTO.setDataTime(dataTime);
            dustDTO.setPm10Value(pm10Value);
            dustDTO.setPm25Value(pm25Value);

            arrayList.add(dustDTO);
        }

        log.info("object={}", object);
        log.info("response={}", response);
        log.info("body={}", body);
        log.info("items={}", itemsArr);

        return arrayList;
    }

}
