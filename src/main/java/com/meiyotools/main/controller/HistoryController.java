package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.History;
import com.meiyotools.main.service.HistoryService;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    private final UserService userService;

    @Autowired
    public HistoryController(HistoryService pHistoryService, UserService pUserService)
    {
        this.historyService = pHistoryService;
        this.userService = pUserService;
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<History> getPlayerHistory(@PathVariable String id)
    {
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<List<History>> getAllHistory()
    {
        return null;
    }

    @GetMapping("/history/day/{date}")
    public ResponseEntity<List<History>> getHistoryDay(@PathVariable LocalDate date)
    {
        return null;
    }


    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<History>> getItemHistory(@PathVariable String itemId)
    {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<History> postHistory(@RequestBody History pHistory, HttpServletRequest request)
    {
        if(this.userService.isLogged(request)) {
            return new ResponseEntity<>(this.historyService.create(pHistory), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(pHistory, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/")
    public ResponseEntity<History> updateHistory(@RequestBody History pHistory, HttpServletRequest request)
    {
        if(this.userService.isLogged(request))
        {
            return new ResponseEntity<>(this.historyService.update(pHistory), HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>(pHistory, HttpStatus.FORBIDDEN);
        }
    }
}
