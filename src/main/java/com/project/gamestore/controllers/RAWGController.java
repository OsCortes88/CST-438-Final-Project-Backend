package com.project.gamestore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gamestore.domain.VideoGame;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

        List<VideoGame> games = new ObjectMapper().readValue(gamesList.toString(), new TypeReference<List<VideoGame>>() {});

        return games;
    }
}
