package com.project.gamestore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.gamestore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        // TODO: Pending review (CARLOS) Add the screenshots to their table in the h2 database
        List<String> screenshotUrls = Collections.singletonList(response.background_image());
        List<Screenshot> screenshotEntities = new ArrayList<>();
        for (String imageUrl : screenshotUrls) {
            Screenshot screenshot = new Screenshot();
            screenshot.setImage_url(imageUrl);
            screenshotEntities.add(screenshot);
        }
        game.setScreenshots(screenshotEntities);
        wishListRepository.save(wishlist);

        // TODO: (FERNANDO) Ass the trailers to their table in the h2 database

        // TODO: (FERNANDO) Add the purchase sites to their table.

        videoGameRepository.save(game);
        wishListRepository.save(wishlist);
        return game;
    }

    // TODO: Pending review (CARLOS) API call to delete a video games from the wishlist
    @DeleteMapping("/delete-game/{wishlistId}")
    public void deleteGameFromWishlist(@PathVariable("wishlistId") Integer wishlistId) throws Exception {
        if (wishListRepository.existsById(wishlistId)) {

            wishListRepository.deleteById(wishlistId);
        } else {
            throw new Exception("wishlist item not found");
        }
    }
    // TODO: Pending review (CARLOS) API for viewing all video games in a users wishlist
    @RestController
    public static class WishlistController {
        private final WishListRepository wishListRepository;
        private final VideoGameRepository videoGameRepository;

        public WishlistController(WishListRepository wishListRepository, VideoGameRepository videoGameRepository) {
            this.wishListRepository = wishListRepository;
            this.videoGameRepository = videoGameRepository;
        }

        public List<VideoGame> viewWishList(@PathVariable("userId") Integer userId) {
            Optional<Wishlist> wishlistsItems = wishListRepository.findById(userId);
            List<VideoGame> videoGames = new ArrayList<>();

            wishlistsItems.ifPresent(wishlistItem -> {
                VideoGame videoGame = wishlistItem.getGame();
                videoGames.add(videoGame);
            });

            return videoGames;
        }
    }
}
