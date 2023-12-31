package com.project.gamestore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.gamestore.domain.VideoGame;
import com.project.gamestore.domain.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin
public class RAWGController {
    @Autowired
    GenreRepository genreRepository;
    private final String key = "eb7f9ac03b794ab6b25ae38079f47a4c";
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/genre-list")
    public  List<Genre> getGenres() {
        return  genreRepository.getGenresByPopularity();
    }

    @GetMapping("/videogames/{size}/{page}")
    public List<VideoGame> ListGames(Principal principal, @PathVariable("size") Integer size,
                                     @PathVariable("page") Integer page) throws JsonProcessingException {
        String url = "https://api.rawg.io/api/games?page_size=" + size +  "&page=" + page + "&key=" + key;
        // Call the RAWG API to get a certain number of games
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        String jsonString = response.getBody();

        // Convert the json string to a json object so we can access data.
        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");
        // Make the list of video games from the json list we retrieved from RAWG
        List<VideoGame> games = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games;
    }

    @GetMapping("/videogames/search/{title}/{size}/{page}")
    public List<VideoGame> ListGamesByName(@PathVariable("title") String title,
                                           @PathVariable("size") Integer size,
                                           @PathVariable("page") Integer page) throws JsonProcessingException {
        processTitle(title);

        String url = "https://api.rawg.io/api/games?search=" + title + "&page_size=" + size + "&page=" + page + "&exclude_additions=true&key=" + key;

        // Call the RAWG API to get a certain number of games.
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        String jsonString = response.getBody();

        // Convert the json string to a json node, so we can access data.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readValue(jsonString, JsonNode.class);

        // Move into the array with the list of games.
        JsonNode gamesList = rootNode.get("results");

        // Iterate through the games and retrieve the game info.
        List<VideoGame> games = new ArrayList<>();
        for (int i = 0; i < gamesList.size(); i++) {
            VideoGame g = new VideoGame();
            g.setId(gamesList.get(i).get("id").asInt());
            g.setName(gamesList.get(i).get("name").asText());
            if(gamesList.get(i).get("description") != null) {
                g.setDescription(gamesList.get(i).get("description").asText());
            }
            g.setBackgroundImage(gamesList.get(i).get("background_image").asText());
            g.setReleased(gamesList.get(i).get("released").asText());
            if(gamesList.get(i).get("esrb_rating").get("slug") != null) {
                g.setEsrb(gamesList.get(i).get("esrb_rating").get("slug").asText());
            }
            g.setRating(gamesList.get(i).get("rating").asDouble());
            g.setPlaytime(gamesList.get(i).get("playtime").asInt());
            games.add(g);
        }
        return games;
    }

    @GetMapping("/videogame-info/{gameId}")
    public VideoGame gameInfo(@PathVariable("gameId") Integer gameId) throws JsonProcessingException {
        String url = "https://api.rawg.io/api/games/" + gameId +  "?key=" + key;
        // Call the RAWG API to get game details
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        // Get the json response
        String jsonString = response.getBody();
        // Convert the json string to a json object so we can access data.
        JSONObject json = new JSONObject(jsonString);
        // Make the list of video games from the json list we retrieved from RAWG
        VideoGame game = new ObjectMapper().readValue(json.toString(), new TypeReference<VideoGame>() {});
        // Get remaining game data
        List<Screenshot> screenshots = getGameScreenShots(gameId);
        List<Trailer> trailers = getGameTrailers(gameId);
        game.setScreenshots(screenshots);
        game.setTrailers(trailers);
        game.setPurchaseSites(getVendors(gameId));
        return game;
    }

    public List<Screenshot> getGameScreenShots(@PathVariable("gameId") Integer gameId) throws JsonProcessingException {
        String url = "https://api.rawg.io/api/games/" + gameId +  "/screenshots?page_size=10&key=" + key;
        // Call the RAWG API to get game details
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        // Get the json response
        String jsonString = response.getBody();
        // Convert the json string to a json object so we can access data.
        JSONObject json = new JSONObject(jsonString);
        JSONArray screenshotList = json.getJSONArray("results");
        // Map data in the json results to the object and return it
        List<Screenshot> screenshots = new ObjectMapper().readValue(screenshotList.toString(), new TypeReference<List<Screenshot>>() {});
        for(Screenshot screenshot : screenshots) {
            screenshot.setGameId(gameId);
        }
        return screenshots;
    }

    public List<Trailer> getGameTrailers(@PathVariable("gameId") Integer gameId) throws JsonProcessingException {
        String url = "https://api.rawg.io/api/games/" + gameId +  "/movies?key=" + key;
        // Call the RAWG API to get game details
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        // Get the json response
        String jsonString = response.getBody();
        // Convert the json string to a json object so we can access data.
        JSONObject json = new JSONObject(jsonString);
        JSONArray trailersList = json.getJSONArray("results");
        // Map data in the json results to the object and return it
        List<Trailer> trailers = new ObjectMapper().readValue(trailersList.toString(), new TypeReference<List<Trailer>>() {});
        for(Trailer trailer : trailers) {
            trailer.setGameId(gameId);
        }
        return trailers;
    }

    public List<PurchaseSite> getVendors(Integer gameId) throws JsonProcessingException {
        String url = "https://api.rawg.io/api/games/" + gameId +  "/stores?key=" + key;
        // Call the RAWG API to get game details
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        // Get the json response
        String jsonString = response.getBody();
        // Convert the json string to a json node so we can access data.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readValue(jsonString, JsonNode.class);
        // Move into the array with the list of stores
        JsonNode vendorList = rootNode.get("results");
        // Iterate through the stores and retrieve the url where we can purchase the game
        List<PurchaseSite> vendors = new ArrayList<>();
        for (int i = 0; i < vendorList.size(); i++) {
            JsonNode vendorSite = vendorList.get(i).get("url");
            String vendor = getVendorName(vendorList.get(i).get("store_id").asInt());
            vendors.add(new PurchaseSite(gameId, vendorSite.asText(), vendor));
        }
        return vendors;
    }
  
    @GetMapping("/videogames-by-genre/{size}/{page}/{genres}")
    public List<VideoGame> listGamesByGenre(@PathVariable("size") Integer pageSize,
                                            @PathVariable("page") Integer pageNum,
                                            @PathVariable("genres") List<String> genres) throws JsonProcessingException {
        // Call the RAWG API to get a certain number of games for a specific genre
        // Combine list of genres to a string that separates each genre with just a comma in between
        StringBuilder combinedGenres = new StringBuilder();
        for (String genre: genres) {
            combinedGenres.append(genre).append(",");
        }
        // Remove unneeded last comma.
        combinedGenres = new StringBuilder(combinedGenres.substring(0, combinedGenres.length() - 1));
        System.out.println(combinedGenres);
        
        
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?page_size=" + pageSize + "&page=" + pageNum + "&genres=" + combinedGenres + "&key=" + key,
                String.class);
        String jsonString = response.getBody();

        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");

        List<VideoGame> games_in_genre = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games_in_genre;
    }

    public String getVendorName(int storeId) throws JsonProcessingException {
        String url = "https://api.rawg.io/api/stores/" + storeId +  "?key=" + key;
        // Call the RAWG API to get game details
        ResponseEntity<String> response = restTemplate.getForEntity(
                url,
                String.class);
        // Get the json response
        String jsonString = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readValue(jsonString, JsonNode.class);

        JsonNode vendorName = rootNode.get("name");
        return vendorName.asText();
    }

    public void processTitle(String title) {
        title = title.replace(' ', '-');
    }
}
