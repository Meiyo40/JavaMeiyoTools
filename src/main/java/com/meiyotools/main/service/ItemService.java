package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Item;
import com.meiyotools.main.model.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final UserService userService;
    private final PlayerService playerService;

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(UserService pUserService, PlayerService pPlayerService, ItemRepository pItemRepository) {
        this.playerService = pPlayerService;
        this.userService = pUserService;
        this.itemRepository = pItemRepository;
    }

    public List<Item> getItems(String pItemName) {

        Optional<List<Item>> itemList = this.itemRepository.findTop8ByNameContaining(pItemName);

        return itemList.orElse(null);
    }

    public Item getItem(long pItemId)
    {
        Optional<Item> item = this.itemRepository.findById(pItemId);

        return item.orElse(null);
    }

    public Item getItem(String pItemName)
    {
        Optional<Item> item = this.itemRepository.findByNameContaining(pItemName);
        return item.orElse(null);
    }
}
