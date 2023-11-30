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
    private final String key = "79fc5d7fcd144b99ade6f0aafc6e8c74";
    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/videogames/{size}/{page}")
    // TODO: Add login verfication using JWT prinipal
    public List<VideoGame> ListGames(@PathVariable("size") Integer size,
                                     @PathVariable("page") Integer page) throws JsonProcessingException {

        String url = "https://api.rawg.io/api/games?page_size=" + size +  "&key=" + key;
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


    // Helper method to process search title's for API call
    public void processTitle(String title) {
        title = title.replace(' ', '-');
    }

    @GetMapping("/videogames/search")
    public List<VideoGame> ListGamesByName(@RequestParam String title) throws JsonProcessingException {
        // Process title.
        processTitle(title);

        String url = "https://api.rawg.io/api/games?search=" + title + "&page_size=10&key=79fc5d7fcd144b99ade6f0aafc6e8c74";

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
            g.setId(Integer.parseInt(String.valueOf(gamesList.get(i).get("id"))));
            g.setName(String.valueOf(gamesList.get(i).get("name")));
            g.setDescription(String.valueOf(gamesList.get(i).get("description")));
            g.setBackgroundImage(String.valueOf(gamesList.get(i).get("background_image")));
            g.setReleased(String.valueOf(gamesList.get(i).get("released")));
            g.setEsrb(String.valueOf(gamesList.get(i).get("esrb_rating").get("slug")));
            g.setRating(Double.parseDouble(String.valueOf(gamesList.get(i).get("rating"))));
            g.setPlaytime(Integer.parseInt(String.valueOf(gamesList.get(i).get("playtime"))));
//            g.setScreenshots(getGameScreenShots(g.getId()));
//            g.setTrailers(getGameTrailers(g.getId()));
//            g.setPurchaseSites(getVendors(g.getId()));
            games.add(g);
        }
        return games;
    }

    @GetMapping("/videogame-info/{gameId}")
    // TODO: Add login verfication using JWT prinipal
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

    @GetMapping("/genre-list")
    // TODO: Add login verfication using JWT prinipal
    public  List<Genre> getGenres() {
        return  genreRepository.getGenresByPopularity();
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
        return trailers;
    }

    public List<String> getVendors(Integer gameId) throws JsonProcessingException {
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
        List<String> vendors = new ArrayList<>();
        for (int i = 0; i < vendorList.size(); i++) {
            JsonNode vendorSite = vendorList.get(i).get("url");
            vendors.add(vendorSite.asText());
        }
        return vendors;
    }
  
  @GetMapping("/videogames/genre")
    public List<VideoGame> listGamesByGenre(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam String genres
    ) throws JsonProcessingException {
        // Call the RAWG API to get a certain number of games for a specific genre
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?page_size=" + pageSize + "&genres=" + genres + "&key=79fc5d7fcd144b99ade6f0aafc6e8c74",
                String.class);
        String jsonString = response.getBody();

        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");

        List<VideoGame> games_in_genre = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games_in_genre;
    }
}
