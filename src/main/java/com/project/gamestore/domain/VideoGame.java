package com.project.gamestore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "video_game")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoGame {
    @Id
    @Column(name = "gameID")
    @JsonProperty("id")
    private int gameId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("released")
    private String released;
    @Column(name = "background_image")
    @JsonProperty("background_image")
    private String background_image;
    @JsonProperty("rating")
    private double rating;
    @JsonProperty("playtime")
    private int playtime;
    @Transient
    private List<Screenshot> screenshots;
    @Transient
    private List<Trailer> trailers;
    @Column(name = "esrb")
    private String esrb;
    @Transient
    private List<PurchaseSite> purchaseSites;

    public VideoGame() {

    }
    public int getId() {
        return gameId;
    }
    public void setId(int id) {
        this.gameId = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getReleased() {
        return released;
    }
    public void setReleased(String released) {
        this.released = released;
    }
    public String getBackground_image() {
        return background_image;
    }
    public void setBackgroundImage(String background_image) {
        this.background_image = background_image;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public int getPlaytime() {
        return playtime;
    }
    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public String getEsrb() {
        return esrb;
    }

    @JsonProperty("esrb_rating")
    private void setEsrb(Map<String,Object> esrb) {
        this.esrb = (String) esrb.get("slug");
    }

    public void setEsrb(String esrb) {
        this.esrb = esrb;
    }

    public List<PurchaseSite> getPurchaseSites() {
        return purchaseSites;
    }

    public void setPurchaseSites(List<PurchaseSite> purchaseSites) {
        this.purchaseSites = purchaseSites;
    }
}
