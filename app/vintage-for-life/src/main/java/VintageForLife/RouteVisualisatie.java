package VintageForLife;

import VintageForLife.API.Router;
import VintageForLife.DB.DBroute;
import VintageForLife.DB.GraphhopperLocatie;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteVisualisatie {
    private SceneController Scene = new SceneController();

    List<GraphhopperLocatie> locationList = new ArrayList<>();

    List<double[]> coordinates = new ArrayList<>();

    List<LocationInfo> routeInfoList = new ArrayList<>();
    List<Node> Route = new ArrayList<>();

    @FXML
    private Label routetxt;

    @FXML
    private Label route_infotxt;

    @FXML
    private ScrollPane Routes = new ScrollPane();

    @FXML
    private WebView mapView = new WebView();

    private DBroute DBroute;
    private int routeID = -1;

    public void setRoute(DBroute route, int routeID) throws IOException {
        this.DBroute = route;
        this.routeID = routeID;
        routetxt.setText("Route: " + route.getId());
        route_infotxt.setText(route.getRoute_info());
        customInitialize();
    }

    private void customInitialize() throws IOException {
        Router router = new Router();

        locationList = DBroute.getLocaties();

        coordinates = router.getRoute(true, locationList);

        addLocations();
        drawRouteOnMap();
    }

    private void addLocations() throws IOException {
        int id = 0;
        for (GraphhopperLocatie location : locationList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationInfo.fxml"));
            Parent content = loader.load();
            Route.add(content);

            LocationInfo controller = loader.getController();
            controller.setDBroute(location, id);

            if (id == 0) {
                controller.setRouteInfo("Start locatie");
            } else if (id == locationList.size() - 1) {
                controller.setRouteInfo("Eind locatie");
            }

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

    public void drawRouteOnMap() {
        int numPoints = coordinates.size();

        double[][] coordinatesArray = new double[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            double[] coordinate = coordinates.get(i);
            double latitude = coordinate[0];
            double longitude = coordinate[1];
            coordinatesArray[i][0] = latitude;
            coordinatesArray[i][1] = longitude;
        }

        drawMap(coordinatesArray, locationList);
    }

    /**
     * Generate a map based on the given coordinates.
     * @param coordinates The coordinates to draw the line on the map.
     * @param locations The locations to set markers on the map.
     */
    private void drawMap(double[][] coordinates, List<GraphhopperLocatie> locations) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<html>\n");
        contentBuilder.append("<head>\n");
        contentBuilder.append("<title>Gerouteerde route</title>\n");
        contentBuilder.append("<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\" />\n");
        contentBuilder.append("<script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>\n");
        contentBuilder.append("</head>\n");
        contentBuilder.append("<body>\n");
        contentBuilder.append("<div id=\"map\" style=\"height: 100%;\"></div>\n");
        contentBuilder.append("<script>\n");
        contentBuilder.append("var map = L.map('map').setView([").append(coordinates[0][0]).append(", ").append(coordinates[0][1]).append("], 13);\n");
        contentBuilder.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);\n");
        contentBuilder.append("L.polyline(").append(Arrays.deepToString(coordinates)).append(", {color: 'blue'}).addTo(map);\n");

        contentBuilder.append("var customIcon = L.icon({\n");
        contentBuilder.append("iconUrl: 'https://www.pngall.com/wp-content/uploads/2017/05/Map-Marker-PNG-File.png',\n"); // Replace with the URL of your custom icon
        contentBuilder.append("iconSize: [32, 32], // size of the icon\n");
        contentBuilder.append("iconAnchor: [16, 32], // point of the icon which will correspond to marker's location\n");
        contentBuilder.append("popupAnchor: [0, -32] // point from which the popup should open relative to the iconAnchor\n");
        contentBuilder.append("});\n");

        for (GraphhopperLocatie location : locations) {
            double latitude = location.getLat();
            double longitude = location.getLon();
            String name = location.getAdres().getStraat() + " " + location.getAdres().getHuisnummer();
            contentBuilder.append("L.marker([").append(latitude).append(", ").append(longitude).append("], {icon: customIcon})")
                    .append(".addTo(map)")
                    .append(".bindPopup('").append(name).append("');\n");
        }

        contentBuilder.append("</script>\n");
        contentBuilder.append("</body>\n");
        contentBuilder.append("</html>\n");

        WebEngine webEngine = mapView.getEngine();
        webEngine.loadContent(contentBuilder.toString());
    }
}
