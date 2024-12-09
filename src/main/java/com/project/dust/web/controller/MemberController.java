package com.project.dust.web.controller;

import com.project.dust.mail.AuthCheckForm;
import com.project.dust.mail.MailCheckForm;
import com.project.dust.mail.MailService;
import com.project.dust.member.EmailDTO;
import com.project.dust.member.Member;
import com.project.dust.member.MemberSaveForm;
import com.project.dust.member.service.MemberService;
import com.project.dust.web.validation.ValidationSequence;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static com.project.dust.web.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    // 회원가입 이메일 검증 정규표현식
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member,
                          @ModelAttribute("email") EmailDTO email) {
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

    @ResponseBody
    @PostMapping("/add/email-validate")
    public MailCheckForm mailConfirm(@RequestBody MailCheckForm emailForm, HttpSession httpSession) {
        String email = emailForm.getEmail();

        log.info("입력 이메일: {}", email);

        String authCode;

        // 1. 이메일 유효 검증
        // 클라이언트에서 1차 검증 후 2차 검증
        if (!isValidEmail(email)) {
            log.info("이메일 형식이 잘못됐습니다.");
            emailForm.setValid(false);
            return emailForm;
        }

        // 2. 이메일 중복 검증
       if (memberService.checkIdDuplicate(email)) {
           log.info("중복된 이메일이 발견됐습니다.");
           emailForm.setDuplication(true);
           return emailForm;
       }

        try {
            authCode = mailService.sendSimpleMessage(email); // 인증코드 메일 발송
            log.info("인증코드: {}", authCode);
        } catch (SendFailedException e) { // 이메일 전송 실패
            log.error("이메일 전송 실패: {}", e.getMessage());
            emailForm.setValid(false);
            return emailForm;
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("예외 발생: {}", e.getMessage());
            emailForm.setValid(false);
            return emailForm;
        }

        log.info("이메일 발송 성공: {}", emailForm);
        emailForm.setValid(true);
        emailForm.setDuplication(false);

        httpSession.setAttribute(AUTH_CODE, authCode);

        return emailForm;
    }


    @ResponseBody
    @PostMapping("/add/confirmCode")
    public AuthCheckForm confirmCode(@RequestBody AuthCheckForm authCheckForm,
                                              @SessionAttribute(name = AUTH_CODE, required = false) String authCode,
                                              HttpSession httpSession) {

        String inputCode = authCheckForm.getInputCode(); // 사용자 입력 코드

        log.info("넘어온 데이터: {}", authCheckForm);
        log.info("사용자 입력코드: {} | 발급 인증코드: {}", inputCode, authCode);

        //입력코드와 인증코드가 다를 경우
        if (!inputCode.equals(authCode)) {
            authCheckForm.setValid(false);
            return authCheckForm;
        }

        authCheckForm.setAuthCode(authCode);
        authCheckForm.setValid(true);

        // 인증 성공 후 세션 해제
        httpSession.invalidate();

        return authCheckForm;
    }

    // 이메일이 빈칸이 아니고 정규표현식과 일치하는지 확인
    private boolean isValidEmail(String email) {
        return StringUtils.hasText(email) && EMAIL_PATTERN.matcher(email).matches();
    }


}
