package com.project.gamestore.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository extends CrudRepository<Wishlist, Integer> {

    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.game = :game")
    void deleteByGame(@Param("game") String game);
}
