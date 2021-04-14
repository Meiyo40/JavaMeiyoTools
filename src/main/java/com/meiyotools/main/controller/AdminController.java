package com.meiyotools.main.controller;

import com.meiyotools.main.service.PageService;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/manager")
public class AdminController {

    private UserService userService;
    private PageService pageService;

    @Autowired
    public AdminController(UserService uService, PageService pageService) {
        this.userService = uService;
        this.pageService = pageService;
    }

    @GetMapping
    public String isLogged(HttpServletRequest request, Model model) {
        if(userService.isLogged(request)) {
            this.pageService.setAdminIndex(request, model);
            return "manager";
        } else {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/players")
    public String getPlayerManager(HttpServletRequest request, Model model) {
        if(userService.isLogged(request)) {
            this.pageService.setPlayerManager(request, model);
            return "manager";
        } else {
            return "redirect:/auth/login";
        }
    }
}
