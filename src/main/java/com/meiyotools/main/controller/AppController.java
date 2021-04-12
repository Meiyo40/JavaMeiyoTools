package com.meiyotools.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {
    @GetMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        String username = (String)request.getSession().getAttribute("user");
        if(username != null) {
            model.addAttribute("logged", "true");
        } else {
            model.addAttribute("logged", "false");
        }
        model.addAttribute("title", "SMB Plan");
        model.addAttribute("page", "manager");

        return "index";
    }
}
