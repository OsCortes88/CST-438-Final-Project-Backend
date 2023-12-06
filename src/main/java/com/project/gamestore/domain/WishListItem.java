package com.project.gamestore.domain;

import javax.persistence.*;

@Entity
@Table(name="wish_list")
@IdClass(WishListItemID.class)
public class WishListItem {
    @Id
    @Column(name = "userID")
    private int userId;
    @Id
    @Column(name = "gameID")
    private int gameId;
    private String name;
    private String background_image;

    public WishListItem() {
    }

    public WishListItem(int userId, int gameId, String name, String background_image) {
        this.userId = userId;
        this.gameId = gameId;
        this.name = name;
        this.background_image = background_image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }
}
