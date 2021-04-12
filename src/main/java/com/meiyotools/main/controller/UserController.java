package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController (UserService pService) {
        this.service = pService;
    }

    @GetMapping
    public ModelAndView redirect() {
        return new ModelAndView("redirect:index");
    }

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody User pUser) {
        User user = service.registerNewUser(pUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
