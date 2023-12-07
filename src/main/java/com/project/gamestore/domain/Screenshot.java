package com.project.gamestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Screenshot {
    @Id
    @Column(name = "gameID")
    private int gameId;
    @JsonProperty("image")
    private String image_url;

    public Screenshot() {
    }

    public Screenshot(int gameId, String image_url) {
        this.gameId = gameId;
        this.image_url = image_url;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screenshot that = (Screenshot) o;

        if (gameId != that.gameId) return false;
        return getImage_url().equals(that.getImage_url());
    }

    @Override
    public int hashCode() {
        int result = gameId;
        result = 31 * result + getImage_url().hashCode();
        return result;
    }
}
