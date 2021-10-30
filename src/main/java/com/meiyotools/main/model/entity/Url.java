package com.meiyotools.main.model.entity;

import javax.persistence.*;

@Entity
@Table
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String originalUrl;
    private String shortUrl;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Url{");
        sb.append("id=").append(id);
        sb.append(", originalUrl='").append(originalUrl).append('\'');
        sb.append(", shortUrl='").append(shortUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
