package com.project.gamestore.domain;

import javax.persistence.*;

@Entity
@Table(name="wish_list")
public class Wishlist {
    @Id
    @Column(name = "userID")
    private int userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameID")
    private VideoGame game;

    public Wishlist() {
        this.userId = 0;
        this.game = new VideoGame();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public VideoGame getGame() {
        return game;
    }

    public void setGame(VideoGame game) {
        this.game = game;
    }
}
