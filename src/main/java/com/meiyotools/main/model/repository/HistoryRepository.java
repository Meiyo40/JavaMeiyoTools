package com.meiyotools.main.model.repository;

import com.meiyotools.main.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAll();
    Optional<History> findById(Long pHistoryId);
    Optional<History> findAllByPlayerName(String pPlayerName);
    Optional<History> findAllByItemName(String pItemName);
    Optional<History> findAllByCreated_at(LocalDate pCreatedAt);
}
