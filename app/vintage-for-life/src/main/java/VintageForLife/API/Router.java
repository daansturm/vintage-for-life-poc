package VintageForLife.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO:
 * - Add route preventions.
 * - Add routes for the Geocoding API.
 */

public class Router {
    private static Router instance; // Instance of this class.
    private final List<double[]> points = new ArrayList<>(); // List that holds all the points for a route.
    private String profile;

    /**
     * Private constructor to prevent external instantiation.
     */
    private Router() {
    }

    /**
     * Method to get the current instance of the Screens.Router class. If it doesn't exist, create a new instance.
     *
     * @return the current instance of the Screens.Router class
     */
    public static Router getInstance() {
        return instance == null ? (instance = new Router()) : instance;
    }

    /**
     * Add a point to the route.
     *
     * @param latitude  the latitude of the point
     * @param longitude the longitude of the point
     */
    public void addPoint(float latitude, float longitude) {
        points.add(new double[]{latitude, longitude});
    }

    /**
     * Add multiple points to the route.
     *
     * @param newPoints the list of points to add to the route
     */
    public void addPoints(List<double[]> newPoints) {
        points.addAll(newPoints);
    }

    /**
     * Get the points of the route.
     *
     * @return the list of points defining the route
     */
    public List<double[]> getPoints() {
        return points;
    }

    /**
     * Set the profile of the route.
     *
     * @param newProfile the profile of the route
     */
    public void setProfile(String newProfile) {
        profile = newProfile;
    }

    /**
     * Get the profile of the route.
     *
     * @return the profile of the route
     */
    public String getProfile() {
        return profile;
    }

    /**
     * Send a POST request to the API endpoint to get the route.
     */
    public String getRoute() {
        String apiUrl = "http://localhost:8989/route"; // TODO: Set API URL
        JSONObject body = new JSONObject();
        body.put("profile", profile);
        body.put("points", new JSONArray(points));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get coordinates from the API and generate an HTML file.
     */
    public void drawRouteOnMap() {
        JSONObject pointList = new JSONObject(getPoints());
        JSONArray paths = pointList.getJSONArray("paths");
        JSONObject firstPath = paths.getJSONObject(0); // Assuming you are working with the first path
        JSONObject pointsObject = firstPath.getJSONObject("points");
        JSONArray coordinates = pointsObject.getJSONArray("coordinates");
        int numPoints = coordinates.length();

        // Create an array for the coordinates
        double[][] coordinatesArray = new double[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            JSONArray coordinate = coordinates.getJSONArray(i);
            double latitude = coordinate.getDouble(0); // Latitude is the first value
            double longitude = coordinate.getDouble(1); // Longitude is the second value
            coordinatesArray[i][0] = latitude;
            coordinatesArray[i][1] = longitude;
        }

        // Use the coordinates to draw the map with Leaflet
        drawMap(coordinatesArray);
    }

    /**
     * Generate a map based on the given coordinates.
     */
    private void drawMap(double[][] coordinates) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<html>\n");
        contentBuilder.append("<head>\n");
        contentBuilder.append("<title>Gerouteerde route</title>\n");
        contentBuilder.append("<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />\n");
        contentBuilder.append("<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>\n");
        contentBuilder.append("</head>\n");
        contentBuilder.append("<body>\n");
        contentBuilder.append("<div id=\"map\" style=\"height: 600px;\"></div>\n");
        contentBuilder.append("<script>\n");
        contentBuilder.append("var map = L.map('map').setView([").append(coordinates[0][0]).append(", ").append(coordinates[0][1]).append("], 13);\n");
        contentBuilder.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);\n");
        contentBuilder.append("L.polyline(").append(Arrays.deepToString(coordinates)).append(", {color: 'blue'}).addTo(map);\n");
        contentBuilder.append("</script>\n");
        contentBuilder.append("</body>\n");
        contentBuilder.append("</html>\n");

        try (FileWriter fileWriter = new FileWriter("map.html")) {
            fileWriter.write(contentBuilder.toString());
        } catch (IOException e) {
            System.err.println("Fout bij schrijven naar het bestand: " + e.getMessage());
        }
    }




}

