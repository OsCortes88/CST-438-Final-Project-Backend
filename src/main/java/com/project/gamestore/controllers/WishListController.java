package com.project.gamestore.controllers;

import com.project.gamestore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WishListController {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    VideoGameRepository videoGameRepository;

    @PutMapping("/add-game/{userID}/{gameID}")
    public boolean addGameToWishList(@PathVariable("userID") Integer userId,
                                       @PathVariable("gameID") Integer gameID) throws Exception {
        // Get the video game data from the RAWGController
        if(wishListRepository.findByGameId(gameID) == null) {
            VideoGameDTO response = restTemplate.getForObject(
                    "http://localhost:8080/videogame-info/" + gameID,
                        VideoGameDTO.class);
            // Set video game info and store it in videogame table
            VideoGame game = new VideoGame();
            game.setId(response.id());
            game.setName(response.name());
            game.setDescription(response.description());
            game.setReleased(response.released());
            game.setBackgroundImage(response.background_image());
            game.setRating(response.rating());
            game.setPlaytime(response.playtime());
            game.setEsrb(response.esrb_rating());
            videoGameRepository.save(game);

            // Create the wishlist object that will later be used in the repository
            WishListItem wishlistItem = new WishListItem();
            wishlistItem.setUserId(userId);
            wishlistItem.setGameId(response.id());
            wishlistItem.setName(response.name());
            wishlistItem.setBackground_image(response.background_image());
            wishListRepository.save(wishlistItem);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such game in the wishlist table");
        }
    }

    @DeleteMapping("/delete-game/{userId}/{gameId}")
    public boolean deleteGameFromWishlist(@PathVariable("userId") Integer userId,
                                       @PathVariable("gameId") Integer gameId) throws Exception {
        if (wishListRepository.findByGameId(gameId) != null) {
            wishListRepository.deleteByGameId(gameId);
            return true;
        } else {
            throw new Exception("wishlist item not found");
        }
    }

    @GetMapping("/wishlist/{userId}")
    public List<WishListItem> getItemsInUserWishList(@PathVariable("userId") Integer userId) {
        List<WishListItem> userWishList = wishListRepository.findUsersWishList(userId);
        return userWishList;
    }
}
