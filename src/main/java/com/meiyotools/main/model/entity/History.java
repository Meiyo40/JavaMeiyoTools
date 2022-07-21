package com.meiyotools.main.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Table
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String playerName;

    private int itemId;

    private String itemName;

    private int cost;

    private LocalDate created_at;

    public History() {
    }

    public History(String playerName, int itemId, String itemName, int cost, LocalDate created_at) {
        this.playerName = playerName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.cost = cost;
        this.created_at = created_at;
    }

    public History(Long id, String playerName, int itemId, String itemName, int cost, LocalDate created_at) {
        this.id = id;
        this.playerName = playerName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.cost = cost;
        this.created_at = created_at;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
