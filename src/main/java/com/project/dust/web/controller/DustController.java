package com.project.dust.web.controller;

import com.project.dust.domain.dust.DustService;
import com.project.dust.domain.dust.MemoryDustRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DustController {

    private final MemoryDustRepository dustRepository;
    private final DustService dustService;

    /**
     * 서비스키	        serviceKey	4	필수	-	공공데이터포털에서 받은 인증키
     * 데이터표출방식	    returnType	4	옵션	xml	xml 또는 json
     * 한 페이지 결과 수	numOfRows	4	옵션	100	한 페이지 결과 수
     * 페이지 번호	        pageNo	    4	옵션	1	페이지번호
     * 시도명	            sidoName	10	필수	서울	시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
     * 오퍼레이션 버전	    ver	        4	옵션	1.0	버전별 상세 결과 참고
     */

    @GetMapping("/dust")
    public String openApi(Model model) throws IOException, ParseException, SQLException {
        /* API 요청 URL */
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=VvAbEIzs7k39WC7z6EbjgcZyXFWCBcR5Da8Kugfd1OXp1WRG7LieBQqDHSIaRgFOHEE62zzCgLqST%2F22LMwHww%3D%3D"); /*Service Key*/
        urlBuilder.append("&").append(URLEncoder.encode("returnType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("json", StandardCharsets.UTF_8)); /*xml 또는 json*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1000", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("sidoName", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("전국", StandardCharsets.UTF_8)); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&").append(URLEncoder.encode("ver", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1.0", StandardCharsets.UTF_8)); /*버전별 상세 결과 참고*/

        URL url = new URL(urlBuilder.toString());

        /* 조합완료된 요청 URL 생성 / HttpURLConnection 객체 활용 API 요청*/
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 요청 결과 출력을 위한 준비
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        // 시스템 자원 반납 및 연결해제
        rd.close();
        conn.disconnect();

        dustService.init(sb.toString());

        model.addAttribute("dustArr", MemoryDustRepository.dustDTOArr);

        return "dust/list";
    }




}
