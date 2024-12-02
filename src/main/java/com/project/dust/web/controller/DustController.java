package com.project.dust.web.controller;

import com.project.dust.dust.Dust;
import com.project.dust.dust.repository.RegionDTO;
import com.project.dust.dust.service.DustService;
import com.project.dust.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
                             @RequestParam("searchWord") String search,
                             Model model) {

        if (!StringUtils.hasText(search)) {
            return "redirect:/";
        }

        Dust dust;

        try {
            dust = dustService.searchDust(search);
        } catch (NoSuchElementException e) {
            return "redirect:/";
        }

        model.addAttribute("dust", dust);
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
        // 스페이스 입력 시 출력X
        if (!StringUtils.hasText(stationName)) {
            return null;
        }

        return dustService.findStationsByName(stationName);
    }

    /**
     * 지역 데이터(지역ID, 지역명) 조회
     * @return List<RegionDTO> 지역정보 리스트
     */
    @ResponseBody
    @PostMapping("/getSidoNames")
    public List<RegionDTO> getSidoNames() {
        return dustService.fetchSidoNames();
    }

}
