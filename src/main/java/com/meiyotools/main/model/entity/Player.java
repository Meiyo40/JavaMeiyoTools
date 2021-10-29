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
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isMain;

    public Player() {
    }

    public Player(String name, String className, String role, String comment, boolean isMain) {
        this.name = name;
        this.className = className;
        this.role = role;
        this.comment = comment;
        this.isMain = isMain;
    }

    public Player(Long id, String name, String className, String role, String comment, boolean isMain) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.role = role;
        this.comment = comment;
        this.isMain = isMain;
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

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Player{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", isMain=").append(isMain);
        sb.append('}');
        return sb.toString();
    }
}
