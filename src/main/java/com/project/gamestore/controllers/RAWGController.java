package com.project.gamestore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gamestore.domain.VideoGame;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class RAWGController {

    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/videogames")
    public List<VideoGame> ListGames() throws JsonProcessingException {

        // Call the RAWG API to get a certain number of games
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?page_size=10&key=79fc5d7fcd144b99ade6f0aafc6e8c74",
                String.class);
        String jsonString = response.getBody();

        // Convert the json string to a json object so we can access data.
        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");
        // Make the list of video games from the json list we retrieved from RAWG
        List<VideoGame> games = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games;
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

    @GetMapping("/videogames/search")
    public List<VideoGame> searchGamesByTitle(@RequestParam String title) throws JsonProcessingException {
        // Call the RAWG API to search for games by title
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?search=" + title + "&key=79fc5d7fcd144b99ade6f0aafc6e8c74",
                //TODO: pending search size limit?
                String.class);
        String jsonString = response.getBody();

        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");

        List<VideoGame> games_by_title = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games_by_title;
    }

    @GetMapping("/videogames/sort/price/low-to-high")
    public List<VideoGame> listGamesByLowToHighPrice(@RequestParam(defaultValue = "10") int pageSize) throws JsonProcessingException {
        // Call the RAWG API to get a certain number of games sorted by price (low to high)
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?page_size=" + pageSize + "&ordering=added&key=79fc5d7fcd144b99ade6f0aafc6e8c74",
                String.class);
        String jsonString = response.getBody();

        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");

        List<VideoGame> games_ltoh = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games_ltoh;
    }

    @GetMapping("/videogames/sort/price/high-to-low")
    public List<VideoGame> listGamesByHighToLowPrice(@RequestParam(defaultValue = "10") int pageSize) throws JsonProcessingException {
        // Call the RAWG API to get a certain number of games sorted by price (high to low)
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?page_size=" + pageSize + "&ordering=-added&key=79fc5d7fcd144b99ade6f0aafc6e8c74",
                String.class);
        String jsonString = response.getBody();

        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");

        List<VideoGame> games_htol = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games_htol;
    }


}
