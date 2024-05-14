package VintageForLife.API;

import VintageForLife.DB.DBadres;
import VintageForLife.DB.GraphhopperLocatie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Geocode {
    private static Geocode instance; // Instance of this class.

    /**
     * Private constructor to prevent external instantiation.
     */
    public Geocode() {
    }

    /**
     * Method to get the current instance of the Screens.Router class. If it doesn't exist, create a new instance.
     *
     * @return the current instance of the Screens.Router class
     */
    public static Geocode getInstance() {
        return instance == null ? (instance = new Geocode()) : instance;
    }

    public GraphhopperLocatie convertAdres(GraphhopperLocatie gl) {


        System.out.println(gl.getId() + " " + gl.getNaam() + " " + gl.getAdres().getStraat());

        String apiUrl = "https://graphhopper.com/api/1/geocode";
        String key = "b02f1fd2-0e8d-41c2-acd5-129bed768b98";
        DBadres adres = gl.getAdres();
        String query = URLEncoder.encode(
                String.join(" ",
                        adres.getStraat(),
                        adres.getHuisnummer(),
                        adres.getPostcode(),
                        adres.getPlaats(),
                        adres.getLand()
                ),
                StandardCharsets.UTF_8
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "?q="+query+"&key="+key))
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Return if the request is not returned with an 200 status.
            if (response.statusCode() != 200) return gl;

            // Create an JSON Object from the response body.
            JSONObject responseObject = new JSONObject(response.body());
            JSONArray hits = responseObject.getJSONArray("hits");
            JSONObject point = hits.getJSONObject(0).getJSONObject("point");

            gl.setLonLan(point.getDouble("lng"), point.getDouble("lat"));
            return gl;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
