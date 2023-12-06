package com.project.gamestore.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface WishListRepository extends CrudRepository<WishListItem, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM WishListItem w WHERE w.gameId = :gameId")
    void deleteByGameId(@Param("gameId") Integer gameId);

    @Query("SELECT w FROM WishListItem w WHERE w.userId = :userId")
    List<WishListItem> findUsersWishList(@Param("userId") Integer userId);

    WishListItem findByGameId(Integer gameId);
}
