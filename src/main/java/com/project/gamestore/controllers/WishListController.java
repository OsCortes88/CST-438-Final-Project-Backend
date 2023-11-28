package com.project.gamestore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.gamestore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WishListController {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    VideoGameRepository videoGameRepository;
    @Autowired
    ScreenshotRepository screenshotRepository;
    @Autowired
    TrailerRepository trailerRepository;
    @Autowired
    PurchaseSiteRepository purchaseSiteRepository;

    @PutMapping("/add-game/{userID}/{gameID}")
    public VideoGame addGameToWishList(@PathVariable("userID") Integer userId,
                                       @PathVariable("gameID") Integer gameID) throws JsonProcessingException {
        // Get the video game data from the RAWGController
        VideoGameDTO response = restTemplate.getForObject(
                "http://localhost:8080/videogame-info/" + gameID,
                VideoGameDTO.class);

        // Create the wishlist object that will later be used in the repository
        Wishlist wishlist = new Wishlist();

        // Set the wishlist attributes
        wishlist.setUserId(userId);

        VideoGame game = new VideoGame();
        game.setId(response.id());
        game.setName(response.name());
        game.setDescription(response.description());
        game.setReleased(response.released());
        game.setBackgroundImage(response.background_image());
        game.setRating(response.rating());
        game.setPlaytime(response.playtime());
        game.setEsrb(response.esrb_rating());
        wishlist.setGame(game);

        // The following todos may require json body to receive the lists and later loop through them and aadd them.
        // TODO: (CARLOS OR FERNANDO) Add the screenshots to their table in the h2 database
        // TODO: (CARLOS OR FERNANDO) Ass the trailers to their table in the h2 database
        // TODO: (CARLOS OR FERNANDO) Add the purchase sites to their table.
        videoGameRepository.save(game);
        wishListRepository.save(wishlist);
        return game;
    }

    // TODO: (CARLOS OR FERNANDO) API call to delete a video games from the wishlist

    // TODO: (CARLOS OR FERNANDO) API for viewing all video games in a users wishlist
}
