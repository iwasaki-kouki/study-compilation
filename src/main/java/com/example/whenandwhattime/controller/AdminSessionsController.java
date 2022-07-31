package com.example.whenandwhattime.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AdminSessionsController {
	
    @GetMapping(path = "/admin/login")
    public String index() {
        return "admin/login";
    }

    @GetMapping(path = "/admin/login-failure")
    public String loginFailure(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-danger");
        model.addAttribute("message", "Emailまたはパスワードに誤りがあります。");

        return "admin/login";
    }

    @GetMapping(path = "/admin/logout-complete")
    public String logoutComplete(Model model) {
        model.addAttribute("hasMessage", true);
        model.addAttribute("class", "alert-info");
        model.addAttribute("message", "ログアウトしました。");

        return "layouts/complete";
    }
}
