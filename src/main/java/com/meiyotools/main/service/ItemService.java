package com.meiyotools.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final UserService userService;
    private final PlayerService playerService;

    @Autowired
    public ItemService(UserService pUserService, PlayerService pPlayerService) {
        this.playerService = pPlayerService;
        this.userService = pUserService;
    }
}
