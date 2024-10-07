package com.project.dust.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "members/login";
    }


}
