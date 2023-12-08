package com.project.gamestore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gamestore.controllers.RAWGController;
import com.project.gamestore.domain.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.project.gamestore.TestUtils.fromJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class JunitTestRAWG {

    @Autowired
    private MockMvc mvc;

    private RAWGController rawg = new RAWGController();

    public List<PurchaseSite> expectedPurchaseSites = new ArrayList<>() {{
        add(new PurchaseSite(3498, "https://store.playstation.com/en-us/product/UP1004-CUSA00419_00-GTAVDIGITALDOWNL", "Play Station Store"));
        add(new PurchaseSite(3498, "https://www.epicgames.com/store/en-US/product/grand-theft-auto-v/home", "Epic Games"));
        add(new PurchaseSite(3498, "http://store.steampowered.com/app/271590/", "STEAM"));
        add(new PurchaseSite(3498, "https://marketplace.xbox.com/en-US/Product/GTA-V/66acd000-77fe-1000-9115-d802545408a7", "XBOX"));
        add(new PurchaseSite(3498, "https://www.microsoft.com/en-us/store/p/grand-theft-auto-v/bpj686w6s0nh?cid=msft_web_chart", "Microsoft"));
    }};

    List<Trailer> expectedTrailers = new ArrayList<>() {{
        add(new Trailer(3498, "GTA Online: Smuggler's Run Trailer", "https://media.rawg.io/media/movies/d8a/d8a61a3a12e52114afdbc28f2c813f5c.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256693661/movie480.mp4"));
        add(new Trailer(3498, "GTA Online: Gunrunning Trailer", "https://media.rawg.io/media/movies/80c/80c2eeb2478d31291dfb5a5fd5a45f2e.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256686767/movie480.mp4"));
        add(new Trailer(3498, "GTA Online: Tiny Racers Trailer", "https://media.rawg.io/media/movies/7c9/7c9f84f3ec31106944a04128287fdd6a.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256683844/movie480.mp4"));
        add(new Trailer(3498, "GTA Online Cunning Stunts: Special Vehicle Circuit Trailer", "https://media.rawg.io/media/movies/d6e/d6e1deb16c4275e8f5769528780e03ac.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256681415/movie480.mp4"));
        add(new Trailer(3498, "GTA Online: Import/Export", "https://media.rawg.io/media/movies/1c1/1c1429a6557185326c1d8c03a6f325c0.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256676519/movie480.mp4"));
        add(new Trailer(3498, "GTA Online: Deadline","https://media.rawg.io/media/movies/f80/f8051b0eb479fa1785c1e04c8e54e322.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256674210/movie480.mp4"));
        add(new Trailer(3498, "GTA Online: Bikers Trailer", "https://media.rawg.io/media/movies/955/9556607dec02bf324c87aa33bba2ed8e.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256672028/movie480.mp4"));
        add(new Trailer(3498, "GTA Online: Cunning Stunts Trailer","https://media.rawg.io/media/movies/66e/66e78864cf57faa2a52cfab4eb6830a4.jpg", "https://steamcdn-a.akamaihd.net/steam/apps/256666646/movie480.mp4"));
    }};

    List<Screenshot> expectedGTAScreenshots = new ArrayList<>() {{
        add(new Screenshot(3498, "https://media.rawg.io/media/screenshots/a7c/a7c43871a54bed6573a6a429451564ef.jpg"));
        add(new Screenshot(3498, "https://media.rawg.io/media/screenshots/cf4/cf4367daf6a1e33684bf19adb02d16d6.jpg"));
        add(new Screenshot(3498, "https://media.rawg.io/media/screenshots/f95/f9518b1d99210c0cae21fc09e95b4e31.jpg"));
        add(new Screenshot(3498, "https://media.rawg.io/media/screenshots/a5c/a5c95ea539c87d5f538763e16e18fb99.jpg"));
        add(new Screenshot(3498, "https://media.rawg.io/media/screenshots/a7e/a7e990bc574f4d34e03b5926361d1ee7.jpg"));
        add(new Screenshot(3498, "https://media.rawg.io/media/screenshots/592/592e2501d8734b802b2a34fee2df59fa.jpg"));
    }};



    @Test
    public void TestListGenres() throws Exception {
        MockHttpServletResponse response;

        // Send the request to the server
        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/genre-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that the request was made successfully
        assertEquals(200, response.getStatus());

        Genre[] genres;
        genres = fromJsonString(response.getContentAsString(), Genre[].class);

        int[] genreIDs = {4, 51, 3, 5, 10, 2,  40,  14, 7,  11, 83, 59, 1, 15, 6, 19, 28, 34, 17};
        for(int i = 0; i < genres.length; i++) {
            assertEquals(genres[i].getId(), genreIDs[i]);
        }
    }

    @Test
    public void TestGetScreenshots() throws Exception {
        List<Screenshot> actualGTAScreenshots = rawg.getGameScreenShots(3498);

        for(int i = 0; i < expectedGTAScreenshots.size(); i++) {
            assertEquals(actualGTAScreenshots.get(i).getGameId(), expectedGTAScreenshots.get(i).getGameId());
            assertEquals(actualGTAScreenshots.get(i).getImage_url(), expectedGTAScreenshots.get(i).getImage_url());
        }
    }

    @Test
    public void TestGetTrailers() throws  Exception{
        List<Trailer> actualTrailers = rawg.getGameTrailers(3498);

        for (int i = 0; i < expectedTrailers.size(); i++) {
            assertEquals(expectedTrailers.get(i).getGameId(), actualTrailers.get(i).getGameId());
            assertEquals(expectedTrailers.get(i).getName(), actualTrailers.get(i).getName());
            assertEquals(expectedTrailers.get(i).getPreview(), actualTrailers.get(i).getPreview());
            assertEquals(expectedTrailers.get(i).getUri(), actualTrailers.get(i).getUri());
        }
    }

    @Test
    public void TestGetVendors() throws  Exception {
        List<PurchaseSite> actualPurchaseSite = rawg.getVendors(3498);

        for (int i = 0 ; i < expectedPurchaseSites.size(); i++) {
            assertEquals(expectedPurchaseSites.get(i).getGameId(), actualPurchaseSite.get(i).getGameId());
            assertEquals(expectedPurchaseSites.get(i).getSite(), actualPurchaseSite.get(i).getSite());
        }
    }

    // Test Game INFO and
    @Test
    public void TestGameInfo() throws Exception {
        MockHttpServletResponse response;

        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/videogame-info/3498")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());

        String jsonString = response.getContentAsString();
        JSONObject json = new JSONObject(jsonString);

        // Set actual data
        VideoGame gameInfo = new VideoGame();

        gameInfo.setId(json.getInt("id"));
        gameInfo.setName(json.getString("name"));
        gameInfo.setDescription(json.getString("description"));
        gameInfo.setReleased(json.getString("released"));
        gameInfo.setBackgroundImage(json.getString("background_image"));
        gameInfo.setRating(json.getDouble("rating"));
        gameInfo.setPlaytime(json.getInt("playtime"));
        gameInfo.setEsrb(json.getString("esrb_rating"));

        JSONArray screenshotArray = json.getJSONArray("screenshots");
        List<Screenshot> actualScreenshots = new ObjectMapper().readValue(screenshotArray.toString(), new TypeReference<List<Screenshot>>() {});
        gameInfo.setScreenshots(actualScreenshots);

        JSONArray trailers = json.getJSONArray("trailers");
        List<Trailer> actualTrailers = new ArrayList<>();
        for(int i = 0; i < trailers.length(); i++) {
            JSONObject trailer = trailers.getJSONObject(i);
            actualTrailers.add(new Trailer(trailer.getInt("gameId"), trailer.getString("name"), trailer.getString("preview"), trailer.getString("data")));
        }
        gameInfo.setTrailers(actualTrailers);

        JSONArray purchaseSites = json.getJSONArray("purchaseSites");
        List<PurchaseSite> actualPurchaseSites = new ArrayList<>();
        for(int i = 0; i < purchaseSites.length(); i++) {
            JSONObject purchaseSite = purchaseSites.getJSONObject(i);
            actualPurchaseSites.add(new PurchaseSite(purchaseSite.getInt("gameId"), purchaseSite.getString("site"), purchaseSite.getString("vendor")));
        }
        gameInfo.setPurchaseSites(actualPurchaseSites);

        // Verify game info
        assertEquals(gameInfo.getId(), 3498);
        assertEquals(gameInfo.getName(), "Grand Theft Auto V");
        assertEquals(gameInfo.getDescription(), "<p>Rockstar Games went bigger, since their previous installment of the series. You get the complicated and realistic world-building from Liberty City of GTA4 in the setting of lively and diverse Los Santos, from an old fan favorite GTA San Andreas. 561 different vehicles (including every transport you can operate) and the amount is rising with every update. <br />\n" +
                "Simultaneous storytelling from three unique perspectives: <br />\n" +
                "Follow Michael, ex-criminal living his life of leisure away from the past, Franklin, a kid that seeks the better future, and Trevor, the exact past Michael is trying to run away from. <br />\n" +
                "GTA Online will provide a lot of additional challenge even for the experienced players, coming fresh from the story mode. Now you will have other players around that can help you just as likely as ruin your mission. Every GTA mechanic up to date can be experienced by players through the unique customizable character, and community content paired with the leveling system tends to keep everyone busy and engaged.</p>\n" +
                "<p>EspaÃ±ol<br />\n" +
                "Rockstar Games se hizo mÃ¡s grande desde su entrega anterior de la serie. Obtienes la construcciÃ³n del mundo complicada y realista de Liberty City de GTA4 en el escenario de Los Santos, un viejo favorito de los fans, GTA San Andreas. 561 vehÃ\u00ADculos diferentes (incluidos todos los transportes que puede operar) y la cantidad aumenta con cada actualizaciÃ³n.<br />\n" +
                "NarraciÃ³n simultÃ¡nea desde tres perspectivas Ãºnicas:<br />\n" +
                "Sigue a Michael, ex-criminal que vive su vida de ocio lejos del pasado, Franklin, un niÃ±o que busca un futuro mejor, y Trevor, el pasado exacto del que Michael estÃ¡ tratando de huir.<br />\n" +
                "GTA Online proporcionarÃ¡ muchos desafÃ\u00ADos adicionales incluso para los jugadores experimentados, reciÃ©n llegados del modo historia. Ahora tendrÃ¡s otros jugadores cerca que pueden ayudarte con la misma probabilidad que arruinar tu misiÃ³n. Los jugadores pueden experimentar todas las mecÃ¡nicas de GTA actualizadas a travÃ©s del personaje personalizable Ãºnico, y el contenido de la comunidad combinado con el sistema de nivelaciÃ³n tiende a mantener a todos ocupados y comprometidos.</p>");
        assertEquals(gameInfo.getReleased(), "2013-09-17");
        assertEquals(gameInfo.getBackground_image(), "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg");
        assertEquals(gameInfo.getRating(), 4.47);
        assertEquals(gameInfo.getPlaytime(), 74);
        assertEquals(gameInfo.getEsrb(), "mature");
        // Verify Screenshots
        for(int i = 0; i < expectedGTAScreenshots.size(); i ++) {
            assertEquals(expectedGTAScreenshots.get(i).getGameId(), gameInfo.getScreenshots().get(i).getGameId());
            assertEquals(expectedGTAScreenshots.get(i).getImage_url(), gameInfo.getScreenshots().get(i).getImage_url());
        }
        // Verify trailers
        for(int i = 0; i < expectedTrailers.size(); i ++) {
            assertEquals(expectedTrailers.get(i).getGameId(), gameInfo.getTrailers().get(i).getGameId());
            assertEquals(expectedTrailers.get(i).getName(), gameInfo.getTrailers().get(i).getName());
            assertEquals(expectedTrailers.get(i).getPreview(), gameInfo.getTrailers().get(i).getPreview());
            assertEquals(expectedTrailers.get(i).getUri(), gameInfo.getTrailers().get(i).getUri());
        }
        // Verify purchase sites
        for(int i = 0; i < expectedPurchaseSites.size(); i ++) {
            assertEquals(expectedPurchaseSites.get(i).getGameId(), gameInfo.getPurchaseSites().get(i).getGameId());
            assertEquals(expectedPurchaseSites.get(i).getSite(), gameInfo.getPurchaseSites().get(i).getSite());
        }
    }

    @Test
    public void TestListGames() throws Exception{
        MockHttpServletResponse response;

        // Make a call to get a list of 10 games
        response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/videogames/10/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());

        JSONArray games = new JSONArray(response.getContentAsString());
        // Verify 10 games were retrieved
        assertEquals(games.length(), 10);

    }

}
