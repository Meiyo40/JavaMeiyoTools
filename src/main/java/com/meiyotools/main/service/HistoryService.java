package com.meiyotools.main.service;

import com.meiyotools.main.model.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final UserService userService;
    private final PlayerService playerService;

    private final ItemRepository itemRepository;

    @Autowired
    public HistoryService(UserService pUserService, PlayerService pPlayerService, ItemRepository pItemRepository) {
        this.playerService = pPlayerService;
        this.userService = pUserService;
        this.itemRepository = pItemRepository;
    }


}
