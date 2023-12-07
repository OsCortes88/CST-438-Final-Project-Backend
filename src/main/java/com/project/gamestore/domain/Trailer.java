package com.project.gamestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trailer {
    @Id
    @Column(name = "gameID")
    private int gameId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("preview")
    private String preview;
    private String uri;

    public Trailer() {
    }

    public Trailer(int gameId, String name, String preview, String uri) {
        this.gameId = gameId;
        this.name = name;
        this.preview = preview;
        this.uri = uri;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getUri() {
        return uri;
    }

    @JsonProperty("data")
    private void setUri(Map<String, Object> data) {
        this.uri = (String) data.get("480");
    }
}
