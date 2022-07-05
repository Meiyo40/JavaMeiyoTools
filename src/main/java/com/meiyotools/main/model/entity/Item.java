package com.meiyotools.main.model.entity;

import javax.persistence.*;

@Entity
@Table
public class Item {

    @Id
    @Column(name = "entry")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "class")
    private int weapon_class;

    @Column(name = "subclass")
    private int weapon_subclass;

    @Column(name = "InventoryType")
    private int inventoryType;

    @Column(name = "ItemLevel")
    private int itemLevel;

    @Column(name = "Quality")
    private int quality;

    @Column(name = "Description")
    private String description;

    public Item() {
    }

    public Item(Long id, String name, int weapon_class, int weapon_subclass, int inventoryType, int itemLevel, int quality, String description) {
        this.id = id;
        this.name = name;
        this.weapon_class = weapon_class;
        this.weapon_subclass = weapon_subclass;
        this.inventoryType = inventoryType;
        this.itemLevel = itemLevel;
        this.quality = quality;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeapon_class() {
        return weapon_class;
    }

    public void setWeapon_class(int weapon_class) {
        this.weapon_class = weapon_class;
    }

    public int getWeapon_subclass() {
        return weapon_subclass;
    }

    public void setWeapon_subclass(int weapon_subclass) {
        this.weapon_subclass = weapon_subclass;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
