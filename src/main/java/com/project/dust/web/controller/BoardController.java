package com.project.dust.web.controller;

import com.project.dust.domain.board.Board;
import com.project.dust.domain.board.BoardForm;
import com.project.dust.domain.board.BoardService;
import com.project.dust.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("member", member);

        return "board/list";
    }

    /**
     * 게시물 상세 페이지
     * @param member 회원
     * @param boardId 게시물 번호
     * @return 상세 페이지
     */
    @GetMapping("/detail/{boardId}")
    public String detail(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                        @PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.findBoardById(boardId);;
        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "board/detail";
    }

    @GetMapping("/add")
    public String addForm(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                          @ModelAttribute("BoardForm") BoardForm form, Model model) {
//        if (member == null) {
//            log.info("로그인 필요");
//            return "redirect:/login";
//        }

        model.addAttribute("member", member);
        model.addAttribute("form", form);

        return "board/addForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("BoardForm") BoardForm form, BindingResult bindingResult,
                      @SessionAttribute(name = LOGIN_MEMBER, required = false) Member member) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "board/addForm";
        }

        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setSidoId(form.getSidoId());
        board.setMemberId(member.getId());

        boardService.createBoard(board);

        return "redirect:/board/list";
    }

}
