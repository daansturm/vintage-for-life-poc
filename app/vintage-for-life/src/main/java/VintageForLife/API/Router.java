package VintageForLife.API;

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

    public List<double[]> getRoute(boolean enabledPoints, List<GraphhopperLocatie> locations) {
        String apiUrl = "https://graphhopper.com/api/1/route?key=5e9d86d6-a117-4d5a-8bde-a6ad07562cf3";

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

            return parseCoordinates(response.body());
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
        routeConfig.put("instructions", false);
        routeConfig.put("points", points);
        routeConfig.put("calc_points", enabledPoints);
        routeConfig.put("points_encoded", false);

        return routeConfig;
    }

    public List<double[]> parseCoordinates(String jsonResponse) {
        List<double[]> coordinatesList = new ArrayList<>();

        JSONObject responseJson = new JSONObject(jsonResponse);
        JSONArray paths = responseJson.getJSONArray("paths");
        JSONObject path = paths.getJSONObject(0);
        JSONArray coordinates = path.getJSONObject("points").getJSONArray("coordinates");

        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coord = coordinates.getJSONArray(i);
            double lon = coord.getDouble(0);
            double lat = coord.getDouble(1);
            coordinatesList.add(new double[]{lat, lon});
        }

        return coordinatesList;
    }
}
