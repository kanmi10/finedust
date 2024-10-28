package com.project.dust.web.controller;

import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberService;
import com.project.dust.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.project.dust.web.SessionConst.*;
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
                                 @RequestBody Map<String, Object> payload,
                                 HttpServletRequest request,
                                 Model model) {

        //회원X
        if (member == null) {
            return "redirect:/";
        }

        String stationName = (String) payload.get("stationName");
        boolean isFavorite = (Boolean) payload.get("favorite");

        log.info("stationName={}, isFavorite={}", stationName, isFavorite);

        //사용자 즐겨찾기 버튼 클릭 유무
        if (isFavorite) {
            memberService.addFavorite(member.getId(), stationName);
        } else {
            memberService.removeFavorite(member.getId(), stationName);
        }

       /* HttpSession session = request.getSession(false);

        Set<String> favorite = memberService.getFavorite(member.getId());
        session.setAttribute(FAVORITES, favorite);*/

        return "loginHome";
    }

    @ResponseBody
    @PostMapping("/getFavorites")
    public Map<String, Object> getFavorites(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member) {
        Map<String, Object> response = new HashMap<>();

        //회원X
        if (member == null) {
            response.put("success", false);
            response.put("message", "회원이 아닙니다.");
            return response;
        }

        Set<String> favorites = memberService.getFavorite(member.getId());
        response.put("success", true);
        response.put("favorites", favorites);
        return response;
    }

}
