package com.meiyotools.main.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String planName;
    private String raidName;
    @Lob
    private String content;
    private LocalDate createdAt;
    @Column(columnDefinition = "integer default 0")
    private int priority;
    @Column(columnDefinition = "integer default 0")
    private int version;

    public Plan() {
    }

    public Plan(String planName, String raidName, String content, LocalDate createdAt) {
        this.planName = planName;
        this.raidName = raidName;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Plan(String planName, String raidName, String content, LocalDate createdAt, int priority, int version) {
        this.planName = planName;
        this.raidName = raidName;
        this.content = content;
        this.createdAt = createdAt;
        this.priority = priority;
        this.version = version;
    }

    public Plan(Long id, String planName, String raidName, String content, LocalDate createdAt, int priority, int version) {
        this.id = id;
        this.planName = planName;
        this.raidName = raidName;
        this.content = content;
        this.createdAt = createdAt;
        this.priority = priority;
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Plan{");
        sb.append("id=").append(id);
        sb.append(", planName='").append(planName).append('\'');
        sb.append(", raidName='").append(raidName).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", priority=").append(priority);
        sb.append(", version=").append(version);
        sb.append('}');
        return sb.toString();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getRaidName() {
        return raidName;
    }

    public void setRaidName(String raidName) {
        this.raidName = raidName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
