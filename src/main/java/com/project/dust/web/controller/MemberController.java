package com.project.dust.web.controller;

import com.project.dust.member.Member;
import com.project.dust.member.MemberSaveForm;
import com.project.dust.member.service.MemberService;
import com.project.dust.web.validation.ValidationSequence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        // 아이디 중복검사
        if (memberService.checkIdDuplicate(form.getLoginId())) {
            bindingResult.rejectValue("loginId", "Duplicate");
        }

        // 이름 중복검사
        if (memberService.checkNameDuplicate(form.getName())) {
            bindingResult.rejectValue("name", "Duplicate");
        }

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
     * 회원탈퇴
     * @param memberId 회원 ID
     */
    @PostMapping("/delete/{memberId}")
    public String delete(@PathVariable("memberId") Long memberId,
                         HttpServletRequest request) {
        // 회원탈퇴
        memberService.markAsDeleted(memberId);

        // 세션 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    /**
     * 즐겨찾기 기능
     */
    @ResponseBody
    @PostMapping("/toggleFavorite")
    public ResponseEntity<String> toggleFavorite(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                                 @RequestBody Map<String, Object> payload) {

//        if (member == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
//        }

        //측정소명과 좋아요 상태 가져옴
        String stationName = (String) payload.get("stationName");
        boolean isFavorite = (Boolean) payload.get("favorite");

        log.info("stationName={}, isFavorite={}", stationName, isFavorite);

        //사용자 즐겨찾기 버튼 클릭 유무
        if (isFavorite) {
            // 꽉찬 하트로 변경되면 북마크 추가
            memberService.addFavorite(member.getId(), stationName);
        } else {
            // 빈 하트로 변경시 북마크 삭제
            memberService.removeFavorite(member.getId(), stationName);
        }

        return ResponseEntity.ok("즐겨찾기 상태가 업데이트됐습니다.");
    }

    @ResponseBody
    @PostMapping("/getFavorites")
    public Map<String, Object> getFavorites(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member) {
        Map<String, Object> response = new HashMap<>();

        log.info("memberId={}", member.getId());
        Set<String> favorites = memberService.getFavorite(member.getId());


        response.put("success", true);
        response.put("favorites", favorites);
        log.info("favorites={}", favorites);
        return response;
    }

}
