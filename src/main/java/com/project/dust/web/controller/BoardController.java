package com.project.dust.web.controller;

import com.project.dust.domain.board.Board;
import com.project.dust.domain.board.BoardEditForm;
import com.project.dust.domain.board.BoardForm;
import com.project.dust.domain.board.BoardService;
import com.project.dust.domain.member.Member;
import com.project.dust.domain.member.MemberService;
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
    private final MemberService memberService;

    @GetMapping("/list")
    public String board(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                        Model model) {
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

        model.addAttribute("member", member);
        model.addAttribute("form", form);

        return "board/addForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("BoardForm") BoardForm form,
                       BindingResult bindingResult,
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

    @GetMapping("/update/{boardId}")
    public String editForm(@PathVariable("boardId") Long boardId,
                           @SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                           Model model) {

        Board board = boardService.findBoardById(boardId);

        // 비회원이거나 자신의 게시물이 아니면 돌아가기
        if (!board.getMemberId().equals(member.getId())) {
            log.info("내 게시물 아님! 게시글 번호={}, 회원 번호={}", boardId, member.getId());
            return "redirect:/board/list";
        }

        log.info("시도번호:{}", board.getSidoId());
        log.info("시도명:{}", board.getSidoName());

        model.addAttribute("boardForm", board);
        model.addAttribute("member", member);

        return "board/editForm";
    }

    @PostMapping("/update/{boardId}")
    public String update(@ModelAttribute("boardForm") BoardEditForm boardEditForm,
                         Model model) {

        Board board = new Board();
        board.setBoardId(boardEditForm.getBoardId());
        board.setTitle(boardEditForm.getTitle());
        board.setContent(boardEditForm.getContent());
        board.setMemberId(boardEditForm.getMemberId());
        board.setSidoId(boardEditForm.getSidoId());

        boardService.updateBoard(board);

        log.info("게시글 수정 완료!");

        return "redirect:/board/detail/" + boardEditForm.getBoardId();
    }



}
