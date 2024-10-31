package com.example.boardproject.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    // PostController -> list()に移動
    @RequestMapping("")
    public String home() {
        return "redirect:/board";
    }
}
