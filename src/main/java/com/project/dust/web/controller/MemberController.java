package com.project.dust.web.controller;

import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberSaveForm;
import com.project.dust.domain.member.MemberService;
import com.project.dust.web.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.project.dust.web.SessionConst.*;

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
    public String save(@Validated(ValidationSequence.class) @ModelAttribute("member") MemberSaveForm form,
                       BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "members/addMemberForm";
        }

        Member member = new Member();
        member.setLoginId(form.getLoginId());
        member.setName(form.getName());
        member.setPassword(form.getPassword());

        memberService.join(member);
        return "redirect:/";
    }


    /**
     * 즐겨찾기 기능
     */
    @ResponseBody
    @PostMapping("/toggleFavorite")
    public ResponseEntity<String> toggleFavorite(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                                 @RequestBody Map<String, Object> payload) {

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
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

        return ResponseEntity.ok("즐겨찾기 상태가 업데이트됐습니다.");
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
