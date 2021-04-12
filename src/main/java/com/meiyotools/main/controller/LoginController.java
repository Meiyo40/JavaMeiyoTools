package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController (UserService pService) {
        this.userService = pService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String logUser(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletRequest request) {
        User user = userService.logUser(username, password);
        if(user != null) {
            request.getSession().setAttribute("user", user.getUsername());
        }
        return "redirect:/index";
    }

    @GetMapping
    public String isLogged(HttpServletRequest request) {
        String username = request.getSession(true).getAttribute("user").toString();
        if(username != null) {
            return username;
        } else {
            return "not found";
        }
    }
}
