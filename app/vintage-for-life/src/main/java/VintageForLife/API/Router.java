package VintageForLife.API;

import VintageForLife.DB.DBadres;
import VintageForLife.DB.DBlevering;
import VintageForLife.DB.GraphhopperLocatie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Router {

    public Router() {
    }

    public String getRoute(boolean enabledPoints, List<GraphhopperLocatie> locations) {
        String apiUrl = "https://graphhopper.com/api/1/route?key=52f77ee9-9f8f-4c94-92ca-80cdbdfb3f44";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .POST(HttpRequest.BodyPublishers.ofString(getRouteConfig(locations, enabledPoints).toString()))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to get route from Graphhopper API: " + response.body());
            }

            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getRouteConfig(List<GraphhopperLocatie> locations, boolean enabledPoints) {
        JSONArray points = new JSONArray();
        for (GraphhopperLocatie location : locations) {
            JSONArray coordinates = new JSONArray();
            coordinates.put(location.getLon());
            coordinates.put(location.getLat());
            points.put(coordinates);
        }

        JSONObject routeConfig = new JSONObject();
        routeConfig.put("profile", "car");
        routeConfig.put("instructions", enabledPoints);
        routeConfig.put("points", points);
        routeConfig.put("calc_points", enabledPoints);
        routeConfig.put("points_encoded", false);

        // Log the route config
        System.out.println(routeConfig);

        return routeConfig;
    }
}
