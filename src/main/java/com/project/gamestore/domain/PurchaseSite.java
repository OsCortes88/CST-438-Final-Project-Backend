package com.project.gamestore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_site")
public class PurchaseSite {
    @Id
    @Column(name = "gameID")
    private int gameId;
    private String site;
    @JsonProperty("vendor")
    private String vendor;

    public PurchaseSite() {
        this.gameId = 0;
        this.site = "";
        this.vendor = "";
    }

    public PurchaseSite(int gameId, String site, String vendor) {
        this.gameId = gameId;
        this.site = site;
        this.vendor = vendor;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
