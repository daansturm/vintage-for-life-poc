package VintageForLife.API;

import VintageForLife.DB.DBadres;
import VintageForLife.DB.GraphhopperLocatie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RouterV2 {

    public RouterV2() {
    }

    public List<GraphhopperLocatie> getRoute(String startLocation, boolean enabledPoints, List<GraphhopperLocatie> locations) {
        String apiUrl = "https://graphhopper.com/api/1/vrp?key=52f77ee9-9f8f-4c94-92ca-80cdbdfb3f44";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .POST(HttpRequest.BodyPublishers.ofString(getRouteConfig(locations, enabledPoints).toString()))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return parseRouteResponse(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<GraphhopperLocatie> parseRouteResponse(String jsonResponse) {
        List<GraphhopperLocatie> route = new ArrayList<>();

        JSONObject responseObject = new JSONObject(jsonResponse);
        JSONArray routesArray = responseObject.getJSONObject("solution").getJSONArray("routes");
        JSONObject routeObject = routesArray.getJSONObject(0); // Assuming only one route for simplicity

        JSONArray activitiesArray = routeObject.getJSONArray("activities");

        for (int i = 0; i < activitiesArray.length(); i++) {
            JSONObject activity = activitiesArray.getJSONObject(i);

            String locationId = activity.getString("location_id");
            double lon = activity.getJSONObject("address").getDouble("lon");
            double lat = activity.getJSONObject("address").getDouble("lat");

            // Assuming the time is in seconds since epoch
            long arrivalTime = activity.getLong("arr_time");

            GraphhopperLocatie locatie = new GraphhopperLocatie(lon, lat, locationId);
            route.add(locatie);
        }

        return route;
    }

    public static JSONObject getRouteConfig(List<GraphhopperLocatie> locations, boolean enabledPoints) {
        JSONObject startAddress = new JSONObject();
        startAddress.put("location_id", "zwolle");
        startAddress.put("lon", 6.08091);
        startAddress.put("lat", 52.50003);

        JSONArray services = new JSONArray();
        for (GraphhopperLocatie location : locations) {
            JSONObject serviceAddress = new JSONObject();
            serviceAddress.put("location_id", location.getId());
            serviceAddress.put("lon", location.getLon());
            serviceAddress.put("lat", location.getLat());

            JSONObject serviceLocation = new JSONObject();
            serviceLocation.put("id", location.getId());
            serviceLocation.put("address", serviceAddress);

            System.out.println(serviceLocation);
            services.put(serviceLocation);
        }

        JSONObject vehicle = new JSONObject();
        vehicle.put("vehicle_id", "vehicle-1");
        vehicle.put("type_id", "small_truck1");
        vehicle.put("start_address", startAddress);
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
        routingConfig.put("calc_points", false);
        routingConfig.put("return_snapped_waypoints", enabledPoints);

        JSONObject configuration = new JSONObject();
        configuration.put("routing", routingConfig);

        JSONObject routeConfig = new JSONObject();
        routeConfig.put("vehicles", vehicles);
        routeConfig.put("vehicle_types", vehicleTypes);
        routeConfig.put("services", services);
        routeConfig.put("objectives", objectives);
        routeConfig.put("configuration", configuration);

        return routeConfig;
    }
}
