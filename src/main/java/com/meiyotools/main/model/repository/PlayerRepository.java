package com.meiyotools.main.model.repository;

import com.meiyotools.main.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String playerName);
    Optional<List<Player>> findByClassName(String className);
    Optional<List<Player>> findByRole(String role);
}
