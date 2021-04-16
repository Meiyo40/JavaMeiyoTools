package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.service.PageService;
import com.meiyotools.main.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Key;

@Controller
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;
    private final PageService pageService;
    public static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    public LoginController (UserService pService, PageService pPageService) {
        this.userService = pService;
        this.pageService = pPageService;
    }

    @GetMapping("/logout")
    public String disconnect(HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession();
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpServletRequest request) {
        if(userService.isLogged(request)) {
            this.pageService.setPlayerManager(request, model);
            return "redirect:/manager";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String logUser(@RequestBody User logs, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.logUser(logs.getUsername(), logs.getPassword());
        if(user != null) {
            request.getSession().setAttribute("user", user.getUsername());
            String jws = Jwts.builder().setSubject(user.getUsername()).signWith(SECRET).compact();
            Cookie token = new Cookie("token", jws);
            token.setMaxAge(5*60);
            token.setPath("/");
            response.addCookie(token);
            return "redirect:/manager";
        }
        return "redirect:/login";
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
