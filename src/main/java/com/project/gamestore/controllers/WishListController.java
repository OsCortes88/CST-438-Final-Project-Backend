package com.project.gamestore.controllers;

import com.project.gamestore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
public class WishListController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    VideoGameRepository videoGameRepository;

    @PutMapping("/add-game/{gameID}")
    public boolean addGameToWishList(Principal principal,
                                     @RequestBody VideoGameDTO gameInfo) throws Exception {
        // Get the video game data from the RAWGController
        User user = userRepository.findByEmail(principal.getName());
        if(wishListRepository.findByGameId((int) user.getId(), gameInfo.id()) == null) {
            // Set video game info and store it in videogame table
            VideoGame game = new VideoGame();
            game.setId(gameInfo.id());
            game.setName(gameInfo.name());
            game.setDescription(gameInfo.description());
            game.setReleased(gameInfo.released());
            game.setBackgroundImage(gameInfo.background_image());
            game.setRating(gameInfo.rating());
            game.setPlaytime(gameInfo.playtime());
            game.setEsrb(gameInfo.esrb_rating());
            videoGameRepository.save(game);

            // Create the wishlist object that will later be used in the repository
            WishListItem wishlistItem = new WishListItem();
            wishlistItem.setUserId((int) user.getId());
            wishlistItem.setGameId(gameInfo.id());
            wishlistItem.setName(gameInfo.name());
            wishlistItem.setBackground_image(gameInfo.background_image());
            wishListRepository.save(wishlistItem);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such game in the wishlist table");
        }
    }

    @DeleteMapping("/delete-game/{gameId}")
    public boolean deleteGameFromWishlist(Principal principal,
                                       @PathVariable("gameId") Integer gameId) throws Exception {
        User user = userRepository.findByEmail(principal.getName());
        if (wishListRepository.findByGameId((int) user.getId(), gameId) != null) {
            wishListRepository.deleteByGameId((int) user.getId(), gameId);

            return true;
        } else {
            throw new Exception("wishlist item not found");
        }
    }

    @GetMapping("/wishlist")
    public List<WishListItem> getItemsInUserWishList(Principal principal) {
        User userInfo = userRepository.findByEmail(principal.getName());
        List<WishListItem> userWishList = wishListRepository.findUsersWishList((int) userInfo.getId());
        return userWishList;
    }
}
