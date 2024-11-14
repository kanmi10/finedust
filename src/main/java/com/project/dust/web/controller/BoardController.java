package com.project.dust.web.controller;

import com.project.dust.domain.board.Board;
import com.project.dust.domain.board.BoardService;
import com.project.dust.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import static com.project.dust.web.SessionConst.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String board(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member, Model model) {
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);

        //로그인을 안한 회원
        if (member == null) {
            return "board/memberBoard";
        }

        model.addAttribute("member", member);
        //로그인 안한 회원
        return "board/memberBoard";
    }

}
