package com.project.dust.web.controller;

import com.project.dust.dust.Dust;
import com.project.dust.dust.service.DustService;
import com.project.dust.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.dust.web.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DustController {

    private final DustService dustService;

    /**
     * 측정소 검색 기능
     * @param member 로그인 회원 객체
     * @param search 검색어
     */
    @GetMapping("/search")
    public String searchDust(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                             @RequestParam("searchWord") String search, Model model) {

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
        return "home";
    }

    /**
     * 측정소 검색어 자동완성 기능
     * @param stationName 측정소명
     * @return 측정소명 집합
     */
    @ResponseBody
    @GetMapping("/searchStations")
    public List<String> searchStations(@RequestParam("stationName") String stationName) {
        return dustService.findStationsByName(stationName);
    }

    @ResponseBody
    @GetMapping("/getSidoNames")
    public Map<String, Object> getSidoNames() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", dustService.fetchSidoNames());
        return response;
    }

}
