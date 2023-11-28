package com.project.gamestore.domain;

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

    public PurchaseSite() {
        this.gameId = 0;
        this.site = "";
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPurchaseSite() {
        return site;
    }

    public void setPurchaseSite(String purchaseSite) {
        this.site = purchaseSite;
    }
}
