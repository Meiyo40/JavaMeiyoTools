package com.meiyotools.main.model.entity;

import javax.persistence.*;

@Entity
@Table
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String className;
    private String role;
    @Column(nullable = true)
    private String comment;

    public Player() {
    }

    public Player(String name, String className, String role, String comment) {
        this.name = name;
        this.className = className;
        this.role = role;
        this.comment = comment;
    }

    public Player(Long id, String name, String className, String role, String comment) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.role = role;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", role='" + role + '\'' +
                ", comment='" + comment + '\'' +
                '}';
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
