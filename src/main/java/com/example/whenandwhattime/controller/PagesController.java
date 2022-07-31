package com.example.whenandwhattime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @RequestMapping("/home")
    public String index() {
        return "pages/home";
    }
}