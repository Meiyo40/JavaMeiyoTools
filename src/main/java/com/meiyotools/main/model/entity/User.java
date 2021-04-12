package com.meiyotools.main.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private LocalDate lastConnection;

    public User() {
    }


    public User(String username, String password, LocalDate lastConnection) {
        this.username = username;
        this.password = password;
        this.lastConnection = lastConnection;
    }


    public User(Long id, String username, String password, LocalDate lastConnection) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastConnection = lastConnection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(LocalDate lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", lastConnection=").append(lastConnection);
        sb.append('}');
        return sb.toString();
    }
}
