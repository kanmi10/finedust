package com.project.dust.web.controller;

import com.project.dust.domain.login.LoginForm;
import com.project.dust.domain.login.LoginService;
import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

import static com.project.dust.web.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "members/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/login";
        }

        try {
            Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

            // 아이디 & 비밀번호 틀림
            if (loginMember == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return "members/login";
            }

            //로그인 성공 처리
            HttpSession session = request.getSession();
            session.setAttribute(LOGIN_MEMBER, loginMember);

            //북마크 상태 조회
            log.info("즐겨찾기 목록={}", memberService.getFavorite(loginMember.getId()));

        } catch (NoSuchElementException e) {
            bindingResult.reject("deletedMember", "이미 탈퇴한 회원입니다.");
            return "members/login";
        }

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
