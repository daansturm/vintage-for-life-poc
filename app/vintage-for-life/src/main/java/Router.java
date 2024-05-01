import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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
     * Method to get the current instance of the Router class. If it doesn't exist, create a new instance.
     *
     * @return the current instance of the Router class
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
    public void getRoute() {
        String apiUrl = "{API URL}"; // TODO: Set API URL

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .POST(HttpRequest.BodyPublishers.ofString("{\"points\":" + pointsToJson() + ",\"profile\":\"" + profile + "\"}"))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert points to JSON format.
     *
     * @return the JSON representation of the points
     */
    private String pointsToJson() {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (double[] point : points) {
            jsonBuilder.append("[").append(point[0]).append(",").append(point[1]).append("],");
        }
        if (!points.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove the last comma
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}
