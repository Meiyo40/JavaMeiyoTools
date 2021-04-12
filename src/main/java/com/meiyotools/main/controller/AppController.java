package com.meiyotools.main.controller;

import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

    private final UserService userService;

    @Autowired
    public AppController(UserService pUserService) {
        this.userService = pUserService;
    }

    @GetMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {

        if(userService.isLogged(request)) {
            model.addAttribute("logged", "true");
        } else {
            model.addAttribute("logged", "false");
        }
        model.addAttribute("title", "SMB Plan");
        model.addAttribute("page", "manager");

        return "index";
    }
}
