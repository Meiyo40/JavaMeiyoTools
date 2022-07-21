package com.meiyotools.main.controller;


import com.meiyotools.main.model.entity.Item;
import com.meiyotools.main.service.ItemService;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    private final UserService userService;

    @Autowired
    public ItemController(ItemService pItemService, UserService pUserService)
    {
        this.itemService = pItemService;
        this.userService = pUserService;
    }

    @GetMapping("/id/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable Long itemId, HttpServletRequest request)
    {
        if(userService.isLogged(request)) {
            Item item = this.itemService.getItem(itemId);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/name/{itemName}")
    public ResponseEntity<List<Item>> getItem(@PathVariable String itemName, HttpServletRequest request)
    {
        if(userService.isLogged(request)) {
            List<Item> items = this.itemService.getItems(itemName);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }
}
