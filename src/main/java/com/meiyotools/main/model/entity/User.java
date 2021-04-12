package com.meiyotools.main.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(255) default ROLE_USER")
    private String rank;
    private LocalDate lastConnection;

    public User() {
    }

    public User(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String email, String password, String rank, LocalDate lastConnection) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rank = rank;
        this.lastConnection = lastConnection;
    }


    public User(Long id, String username, String email, String password, String rank, LocalDate lastConnection) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.rank = rank;
        this.lastConnection = lastConnection;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        sb.append(", email='").append(email).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", lastConnection=").append(lastConnection);
        sb.append('}');
        return sb.toString();
    }
}
