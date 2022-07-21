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

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository pHistoryRepository) {
        this.historyRepository = pHistoryRepository;
    }

    public History create(History pHistory) {
        pHistory.setCreated_at(LocalDate.now());
        return this.historyRepository.save(pHistory);
    }

    public History update(History pHistory)
    {
        return this.historyRepository.save(pHistory);
    }

    public History getHistory(Long pHistoryId)
    {
        return this.historyRepository.findById(pHistoryId).orElse(null);
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

    /**
     * Delete the history at the given ID
     * @param pHistoryId
     */
    public void deleteHistory(Long pHistoryId)
    {
        this.historyRepository.deleteById(pHistoryId);
    }
}
