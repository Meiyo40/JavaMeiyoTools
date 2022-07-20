package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.History;
import com.meiyotools.main.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService pHistoryService)
    {
        this.historyService = pHistoryService;
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<History> getPlayerHistory(@PathVariable String id)
    {
        return null;
    }

    @GetMapping("/history")
    public ResponseEntity<List<History>> getAllHistory()
    {
        return null;
    }

    @GetMapping("/history/day/{date}")
    public ResponseEntity<List<History>> getHistoryDay(@PathVariable LocalDate date)
    {
        return null;
    }


    @GetMapping("/history/item/{itemId}")
    public ResponseEntity<List<History>> getItemHistory(@PathVariable String itemId)
    {
        return null;
    }
}
