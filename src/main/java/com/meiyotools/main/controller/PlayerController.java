package com.meiyotools.main.controller;

import com.meiyotools.main.model.entity.Player;
import com.meiyotools.main.service.PlayerService;
import com.meiyotools.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService service;
    private final UserService userService;

    @Autowired
    public PlayerController(PlayerService pService, UserService pUService) {
        this.service = pService;
        this.userService = pUService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return new ResponseEntity<>(service.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/name/{playerName}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String playerName) {
        return new ResponseEntity<>(service.getPlayerByName(playerName), HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Player>> getPlayersByRole(@PathVariable String role) {
        return new ResponseEntity<>(service.getPlayersByRole(role), HttpStatus.OK);
    }

    @GetMapping("/class/{className}")
    public ResponseEntity<List<Player>> getPlayersByClass(@PathVariable String className) {
        return new ResponseEntity<>(service.getPlayersByClass(className), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer (@RequestBody Player player, HttpServletRequest request) {
        if(userService.isLogged(request)) {
            return new ResponseEntity<>(service.newPlayer(player), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(player, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable Long id, HttpServletRequest request) {
        if(userService.isLogged(request)) {
            player.setId(id);
            return new ResponseEntity<>(service.updatePlayer(player), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(player, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        service.deletePlayer(id);
    }
}
