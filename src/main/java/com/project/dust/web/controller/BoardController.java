package com.project.dust.web.controller;

import com.project.dust.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.project.dust.web.SessionConst.LOGIN_MEMBER;

@Controller
@Slf4j
@RequestMapping("board")
public class BoardController {

    @GetMapping("/list")
    public String board(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member, Model model) {

        //로그인을 안한 회원
        if (member == null) {
            return "board/list";
        }

        model.addAttribute("member", member);
        //로그인 안한 회원
        return "board/list";
    }

}