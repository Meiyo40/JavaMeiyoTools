package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.History;
import com.meiyotools.main.model.repository.HistoryRepository;
import com.meiyotools.main.model.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryService {

    private final UserService userService;
    private final PlayerService playerService;

    private final ItemRepository itemRepository;

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(UserService pUserService, HistoryRepository pHistoryRepository, PlayerService pPlayerService, ItemRepository pItemRepository) {
        this.playerService = pPlayerService;
        this.userService = pUserService;
        this.historyRepository = pHistoryRepository;
        this.itemRepository = pItemRepository;
    }

    public History create(History pHistory) {
        pHistory.setCreated_at(LocalDate.now());
        return this.historyRepository.save(pHistory);
    }

    public History update(History pHistory)
    {
        return this.historyRepository.save(pHistory);
    }

    /**
     * @param pPlayerName
     * @return History of the given playerName.
     */
    public List<History> getHistory(String pPlayerName)
    {
        return this.historyRepository.findAllByPlayerName(pPlayerName).orElse(null);
    }

    /**
     * @return All history of all players
     */
    public List<History> getHistory()
    {
        return this.historyRepository.findAll();
    }
}
