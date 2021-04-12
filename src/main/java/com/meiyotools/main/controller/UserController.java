package com.meiyotools.main.controller;

import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController (UserService pService) {
        this.service = pService;
    }
}
