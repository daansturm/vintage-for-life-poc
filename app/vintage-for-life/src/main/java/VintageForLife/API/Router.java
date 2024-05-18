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

    public List<GraphhopperLocatie> getRoute(boolean enabledPoints, List<GraphhopperLocatie> locations) {
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

            return parseCoordinatesFromResponse(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<GraphhopperLocatie> parseCoordinatesFromResponse(String jsonResponse) {
        List<GraphhopperLocatie> route = new ArrayList<>();

        JSONObject responseObject = new JSONObject(jsonResponse);

        // Log the response
        System.out.println(responseObject);

        JSONArray routesArray = responseObject.getJSONObject("paths").getJSONArray("points");

        if (routesArray.length() == 0) {
            return route;
        }

        JSONObject routeObject = routesArray.getJSONObject(0);

        JSONArray activitiesArray = routeObject.getJSONArray("activities");

        for (int i = 0; i < activitiesArray.length(); i++) {
            JSONObject activity = activitiesArray.getJSONObject(i);

            String locationId = activity.getString("location_id");
            double lon = activity.getJSONObject("address").getDouble("lon");
            double lat = activity.getJSONObject("address").getDouble("lat");

            route.add(new GraphhopperLocatie(lon, lat, locationId));
        }

        return route;
    }

    public static JSONObject getRouteConfig(List<GraphhopperLocatie> locations, boolean enabledPoints) {
//        JSONObject startAddress = new JSONObject();
//        startAddress.put("location_id", "start");
//        startAddress.put("lon", startLocation.getLon());
//        startAddress.put("lat", startLocation.getLat());

        JSONArray points = new JSONArray();
        for (GraphhopperLocatie location : locations) {

            JSONArray coordinates = new JSONArray();
            coordinates.put(location.getLon());
            coordinates.put(location.getLat());

            points.put(coordinates);
        }

        JSONObject vehicle = new JSONObject();
        vehicle.put("vehicle_id", "vehicle-1");
        vehicle.put("type_id", "small_truck1");
//        vehicle.put("start_address", startAddress);
        vehicle.put("earliest_start", 1715227200);
        vehicle.put("latest_end", 1715256000);
        vehicle.put("max_jobs", 10);
        vehicle.put("return_to_depot", true);

        JSONArray vehicles = new JSONArray();
        vehicles.put(vehicle);

        JSONObject vehicleType = new JSONObject();
        vehicleType.put("type_id", "small_truck1");
        vehicleType.put("capacity", new JSONArray().put(10));
        vehicleType.put("profile", "small_truck");

        JSONArray vehicleTypes = new JSONArray();
        vehicleTypes.put(vehicleType);

        JSONArray objectives = new JSONArray();
        objectives.put(new JSONObject().put("type", "min").put("value", "vehicles"));
        objectives.put(new JSONObject().put("type", "min").put("value", "completion_time"));

        JSONObject routingConfig = new JSONObject();
        routingConfig.put("calc_points", enabledPoints);
        routingConfig.put("return_snapped_waypoints", enabledPoints);

        JSONObject configuration = new JSONObject();
        configuration.put("routing", routingConfig);

        JSONObject routeConfig = new JSONObject();
        routeConfig.put("vehicles", vehicles);
        routeConfig.put("vehicle_types", vehicleTypes);
        routeConfig.put("objectives", objectives);
        routeConfig.put("configuration", configuration);

        return routeConfig;
    }
}
