package com.meiyotools.main.controller;

import com.meiyotools.main.service.PageService;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

    private final UserService userService;
    private final PageService pageService;

    @Autowired
    public AppController(UserService pUserService, PageService pageService) {
        this.pageService = pageService;
        this.userService = pUserService;
    }

    @GetMapping(value = {"/index", "/"})
    public String getIndex(Model model, HttpServletRequest request) {

        this.pageService.setPublicIndex(request, model);

        return "index";
    }

    @GetMapping("/teron")
    public String getTeronGame(Model model, HttpServletRequest request) {

        this.pageService.setTeronPage(request, model);

        return "index";
    }
}
