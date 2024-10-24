package com.project.dust.web.controller;

import com.project.dust.domain.dust.JdbcDustRepository;
import com.project.dust.domain.member.JdbcMemberRepository;
import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberRepository;
import com.project.dust.domain.member.MemoryMemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Slf4j
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController() {
        this.memberRepository = new JdbcMemberRepository();
    }

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

        memberRepository.save(member);
        return "redirect:/";
    }


}
