package com.project.dust.web.controller;

import com.project.dust.domain.dust.Dust;
import com.project.dust.domain.dust.DustService;
import com.project.dust.domain.dust.MemoryDustRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DustController {

    private final MemoryDustRepository dustRepository;
    private final DustService dustService;

    @GetMapping("/dust")
    @ResponseBody
    public String dust() throws IOException, ParseException, SQLException {
        dustService.init();
        return "ok";
    }

    @GetMapping("/search")
    public String searchDust(@RequestParam("searchWord") String search, Model model) throws SQLException {
        if (!StringUtils.hasText(search)) {
            return "redirect:/";
        }

        Dust dust = dustService.searchDust(search);
        log.info("controller.dust={}", dust);
        model.addAttribute("dust", dust);
        return "home";
    }


}
