package com.project.gamestore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gamestore.domain.VideoGame;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    // Helper method to process search title's for API call
    public void processTitle(String title) {
        title = title.replace(' ', '-');
    }

    @GetMapping("/videogames/search")
    public List<VideoGame> ListGamesByName(@RequestParam String title) throws JsonProcessingException {
        // Process title
        processTitle(title);

        // Call the RAWG API to get a certain number of games
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.rawg.io/api/games?search=" + title + "&page_size=10&key=79fc5d7fcd144b99ade6f0aafc6e8c74",
                String.class);
        String jsonString = response.getBody();

        // Convert the json string to a json object, so we can access data.
        JSONObject json = new JSONObject(jsonString);
        JSONArray gamesList = json.getJSONArray("results");
        List<VideoGame> games = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});
        return games;
    }
}
