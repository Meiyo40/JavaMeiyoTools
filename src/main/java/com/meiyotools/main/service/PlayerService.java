package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Player;
import com.meiyotools.main.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    @Autowired
    public PlayerService (PlayerRepository repository) {
        this.repository = repository;
    }

    public Player getPlayerByName(String name) {
        Optional<Player> player = this.repository.findByName(name);
        return player.isPresent() ? player.get() : null;
    }

    public List<Player> getAllPlayers() {
        Optional<List<Player>> list = repository.findAllByOrderByClassName();
        return list.isPresent() ? list.get() : null;
    }

    public List<Player> getPlayersByRole(String role) {
        Optional<List<Player>> list = repository.findByRole(role);
        return list.isPresent() ? list.get() : null;
    }

    public List<Player> getPlayersByClass(String className) {
        Optional<List<Player>> list = repository.findByClassName(className);
        return list.isPresent() ? list.get() : null;
    }

    public Player newPlayer(Player player) {
        return repository.save(player);
    }

    public Player updatePlayer(Player player) {
        return repository.save(player);
    }

    public void deletePlayer(Long id) {
        repository.deleteById(id);
    }
}
