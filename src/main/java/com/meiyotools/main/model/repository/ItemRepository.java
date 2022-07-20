package com.meiyotools.main.model.repository;

import com.meiyotools.main.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long ItemId);
    Optional<Item> findByNameContaining(String ItemName);
    Optional<List<Item>> findAllByNameContaining(String ItemName);
    Optional<List<Item>> findTop8ByNameContaining(String itemName);
}
