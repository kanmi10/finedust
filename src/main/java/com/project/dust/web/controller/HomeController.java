package com.project.dust.web.controller;

import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberRepository;
import com.project.dust.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.project.dust.web.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member, Model model) {

        //세션에 회원 데이터가 없으면 home
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }




}
