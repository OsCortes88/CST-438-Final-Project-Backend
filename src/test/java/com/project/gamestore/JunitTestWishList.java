package com.project.gamestore;

import com.project.gamestore.controllers.WishListController;
import com.project.gamestore.domain.VideoGameDTO;
import com.project.gamestore.domain.WishListItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestWishList {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WishListController wishList;

    private final Principal principal = new Principal() {
        @Override
        public String getName() {
            return "test@csumb.edu";
        }
    };

    private VideoGameDTO grandTheftAuto5  = new VideoGameDTO(3498, "Grand Theft Auto V", "", "2013-09-17", "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg", 4.47, 74, "mature");
    private VideoGameDTO fortnite = new VideoGameDTO(47137, "Fortnite Battle Royale", "", "2017-09-26", "https://media.rawg.io/media/games/dcb/dcbb67f371a9a28ea38ffd73ee0f53f3.jpg", 3.21, 0,"teen");

    // Add games to "expected" wishlist for testing.
    public List<WishListItem> expectedWishList = new ArrayList<>() {{
        add(new WishListItem(
                1,
                47137,
                "Fortnite Battle Royale",
                "https://media.rawg.io/media/games/dcb/dcbb67f371a9a28ea38ffd73ee0f53f3.jpg"
        ));
                add(new WishListItem(
                1,
                3498,
                "Grand Theft Auto V",
                "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg"
        ));
    }};

    @Test
    public void TestAddGameToWishList() throws Exception {
        // Compare the size of the "actual" wishlist before inserting games.
        List<WishListItem> actualWishList = wishList.getItemsInUserWishList(principal);
        assertEquals(0, actualWishList.size());

        // Compare size after 1 game
        wishList.addGameToWishList(principal, fortnite);
        actualWishList = wishList.getItemsInUserWishList(principal);
        assertEquals(1, actualWishList.size());

        // Compare size after 2 games
        wishList.addGameToWishList(principal, grandTheftAuto5);
        actualWishList = wishList.getItemsInUserWishList(principal);
        assertEquals(2, actualWishList.size());

        // Delete games from wishlist (reset)
        wishList.deleteGameFromWishlist(principal, fortnite.id());
        wishList.deleteGameFromWishlist(principal, grandTheftAuto5.id());
    }

    @Test
    public void TestDeleteGameFromWishList() throws Exception {
        // Insert games to the "actual" wishlist
        wishList.addGameToWishList(principal, fortnite);
        wishList.addGameToWishList(principal, grandTheftAuto5);

        // Check size of wishlist before removal
        List<WishListItem> actualWishList = wishList.getItemsInUserWishList(principal);
        assertEquals(2, actualWishList.size());

        // Check size after 1 removal
        wishList.deleteGameFromWishlist(principal, fortnite.id());
        actualWishList = wishList.getItemsInUserWishList(principal);
        assertEquals(1, actualWishList.size());

        // Check size after 2 removals
        wishList.deleteGameFromWishlist(principal, grandTheftAuto5.id());
        actualWishList = wishList.getItemsInUserWishList(principal);
        assertEquals(0, actualWishList.size());
    }

    @Test
    public void TestGetItemsInWishList() throws Exception {
        // Insert games to "actual" wishlist
        wishList.addGameToWishList(principal, fortnite);
        wishList.addGameToWishList(principal, grandTheftAuto5);
        List<WishListItem> actualWishList = wishList.getItemsInUserWishList(principal);

        // "Actual" wishlist is reversed, so reverse "expected" to avoid mismatch
        Collections.reverse(expectedWishList);

        for (int i = 0; i < expectedWishList.size(); i++) {
            // Iterate and compare both wishlists
            assertEquals(actualWishList.get(i).getGameId(), expectedWishList.get(i).getGameId());
            assertEquals(actualWishList.get(i).getName(), expectedWishList.get(i).getName());
            assertEquals(actualWishList.get(i).getUserId(), expectedWishList.get(i).getUserId());
            assertEquals(actualWishList.get(i).getBackground_image(), expectedWishList.get(i).getBackground_image());
        }
        // Reset
        wishList.deleteGameFromWishlist(principal, fortnite.id());
        wishList.deleteGameFromWishlist(principal, grandTheftAuto5.id());
    }
}