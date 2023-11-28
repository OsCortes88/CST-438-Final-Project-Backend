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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
