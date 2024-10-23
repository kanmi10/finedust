package com.project.dust.web.controller;

import com.project.dust.domain.dust.Dust;
import com.project.dust.domain.dust.DustService;
import com.project.dust.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.project.dust.web.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DustController {

    private final DustService dustService;

   /* @GetMapping("/dust")
    @ResponseBody
    public String dust() throws IOException, ParseException, SQLException {
        dustService.init();
        return "ok";
    }*/

    /**
     * 측정소 검색 기능
     * @param member 로그인 회원 객체
     * @param search 검색어
     */
    @GetMapping("/search")
    public String searchDust(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                             @RequestParam("searchWord") String search, Model model) throws SQLException, ParseException, IOException {

        if (!StringUtils.hasText(search)) {
            return "redirect:/";
        }

        Dust dust = dustService.searchDust(search);
        log.info("controller.dust={}", dust);
        model.addAttribute("dust", dust);

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    /**
     * 측정소 검색어 자동완성 기능
     * @param stationName 측정소명
     * @return 측정소명 집합
     */
    @ResponseBody
    @GetMapping("/searchStations")
    public List<String> searchStations(@RequestParam("stationName") String stationName) throws SQLException {
        return dustService.findStationsByName(stationName);
    }


}
