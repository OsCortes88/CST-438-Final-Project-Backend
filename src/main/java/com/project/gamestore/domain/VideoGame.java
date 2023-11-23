package com.project.gamestore.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoGame {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("released")
    private String released;
    @JsonProperty("background_image")
    private String background_image;
    @JsonProperty("rating")
    private double rating;
    @JsonProperty("playtime")
    private int playtime;

    private List<Screenshot> screenshots;

    private List<Trailer> trailers;

    private String esrb;

    private List<String> vendorSites;

    public VideoGame() {

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public void setBackground_image(String background_image) {
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

    public List<String> getVendorSites() {
        return vendorSites;
    }

    public void setVendorSites(List<String> vendorSites) {
        this.vendorSites = vendorSites;
    }
}
