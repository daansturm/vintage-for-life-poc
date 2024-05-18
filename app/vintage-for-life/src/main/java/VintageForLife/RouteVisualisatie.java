package VintageForLife;

import VintageForLife.API.Geocode;
import VintageForLife.API.Router;
import VintageForLife.DB.DBlevering;
import VintageForLife.DB.DBroute;
import VintageForLife.DB.GraphhopperLocatie;
import VintageForLife.Routes.APPRoutes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteVisualisatie implements RouteListener {

    private SceneController Scene = new SceneController();

    List<GraphhopperLocatie> locationList = new ArrayList<>();

    List<GraphhopperLocatie> locatiesCor = new ArrayList<>();
    List<LocationInfo> routeInfoList = new ArrayList<>();
    List<Node> Route = new ArrayList<>();

    private Geocode gc = new Geocode();

    @FXML
    private Label routetxt;
    @FXML
    private Label route_infotxt;
    @FXML
    private ScrollPane Routes = new ScrollPane();
    @FXML
    private ScrollPane locaties = new ScrollPane();
    @FXML
    private WebView mapView = new WebView();

    private DBroute DBroute;
    private int routeID = -1;

    public void setRoute(DBroute route, int routeID) throws SQLException, IOException {
        this.DBroute = route;
        this.routeID = routeID;
        routetxt.setText("Route: " + route.getId());
        route_infotxt.setText(route.getRoute_info());
        customInitialize();
    }

    private void customInitialize() throws SQLException, IOException {
        addLocations();

        Router router = new Router();

        locationList = DBroute.getLocaties();

        List<GraphhopperLocatie> graphhopperLocaties = router.getRoute(true, locationList);


        System.out.println("RouteVisualisatie: " + graphhopperLocaties.size());
        for (GraphhopperLocatie locatie : graphhopperLocaties) {
            System.out.println(locatie);
        }

//        drawRouteOnMap();
    }

    private void addLocations() throws IOException {
        int id = 0;
        for (GraphhopperLocatie location : locationList) {
            location.Print();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationInfo.fxml"));
            Parent content = loader.load();
            Route.add(content);

            LocationInfo controller = loader.getController();
//            controller.setDBroute(location, id);
            routeInfoList.add(controller);
            id++;
        }

        VBox items = new VBox(Routes.getWidth());
        items.getChildren().addAll(Route);
        items.prefWidthProperty().bind(Routes.widthProperty());
        Routes.setContent(items);
    }

    @FXML
    private void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);
    }

    @FXML
    void Routes(ActionEvent event) throws IOException {
        Scene.ShowOverzichtRoutes(event);
    }

    @Override
    public void onRouteSelected(int ID) {
    }

    public void drawRouteOnMap() {
        // TODO: Get the coordinates from the API

//        int numPoints = coordinates.length();
//
//        // Create an array for the coordinates
//        double[][] coordinatesArray = new double[numPoints][2];
//
//        for (int i = 0; i < numPoints; i++) {
//            JSONArray coordinate = coordinates.getJSONArray(i);
//            double latitude = coordinate.getDouble(0); // Latitude is the first value
//            double longitude = coordinate.getDouble(1); // Longitude is the second value
//            coordinatesArray[i][0] = latitude;
//            coordinatesArray[i][1] = longitude;
//        }
//
//        // Use the coordinates to draw the map with Leaflet
//        drawMap(coordinatesArray);
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
            WebEngine webEngine = mapView.getEngine();
            webEngine.load(getClass().getResource("map.html").toExternalForm());
        } catch (IOException e) {
            System.err.println("Fout bij schrijven naar het bestand: " + e.getMessage());
        }
    }

    // Dummy method to simulate getting points data
    private String getPoints() {
        return "{\n" +
                "  \"paths\": [\n" +
                "    {\n" +
                "      \"points\": {\n" +
                "        \"coordinates\": [\n" +
                "          [51.505, -0.09],\n" +
                "          [51.51, -0.1],\n" +
                "          [51.51, -0.12]\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
