package com.project.dust.web.controller;

import com.project.dust.domain.board.*;
import com.project.dust.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import static com.project.dust.web.SessionConst.LOGIN_MEMBER;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/list")
    public String board(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(value = "searchType", required = false) Integer typeNumber,
                        @RequestParam(value = "searchWord", required = false) String word,
                        @SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                        Model model) {

        int pageSize = 10;// 페이지 당 게시물 수

        Page<Board> boards;

        // 검색어 유무
        if (!StringUtils.hasText(word)) {
            boards = boardService.getBoards(page, pageSize);

        } else {
            boards = boardService.searchBoards(typeNumber, word, page, pageSize);
            model.addAttribute("searchType", typeNumber);
            model.addAttribute("searchWord", word);

        }

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

    @PostMapping("/delete/{boardId}")
    public String delete(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoardById(boardId);
        return "redirect:/board/list";
    }

    //@GetMapping("/search")
    public String search(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member member,
                         @RequestParam("searchType") int typeNumber,
                         @RequestParam("searchWord") String word,
                         @RequestParam(defaultValue = "1") int page,
                         Model model) {

        int pageSize = 10;

        Page<Board> boards = boardService.searchBoards(typeNumber, word, page, pageSize);
        log.info("boards={}", boards);

        model.addAttribute("boards", boards);
        model.addAttribute("searchType", typeNumber);
        model.addAttribute("searchWord", word);
        model.addAttribute("member", member);

        return "/board/searchList";
    }

}
