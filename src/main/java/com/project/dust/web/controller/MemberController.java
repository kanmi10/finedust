package com.project.dust.web.controller;

import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

import static com.project.dust.web.SessionConst.LOGIN_MEMBER;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("member") Member member, BindingResult result) throws SQLException {
        if (result.hasErrors()) {
            log.info("error={}", result);

            return "members/addMemberForm";
        }

        memberService.join(member);
        return "redirect:/";
    }



    /**
     * 즐겨찾기 기능
     */
    @ResponseBody
    @PostMapping("/toggleFavorite")
    public String toggleFavorite(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                                 @RequestBody Map<String, Object> payload) {

        if (member == null) {
            return "redirect:/";
        }

        String stationName = (String) payload.get("stationName");
        boolean isFavorite = (Boolean) payload.get("favorite");

        log.info("stationName={}, isFavorite={}", stationName, isFavorite);

       /* if (isFavorite) {
            memberService.addFavorite(stationName);
        } else {
            memberService.removeFavorite(stationName);
        }*/

        return "home";
    }
}
