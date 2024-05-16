package VintageForLife;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteVisualisatie implements RouteListener {

    private SceneController Scene = new SceneController();


    List<DBlevering> locationList = new ArrayList<>();

    List<LocationInfo> routeInfoList = new ArrayList<>();

    List<Node> Route = new ArrayList<>();

    @FXML
    private Label routetxt;

    @FXML
    private Label route_infotxt;

    @FXML
    private ScrollPane Routes = new ScrollPane();

    @FXML
    private ScrollPane locaties = new ScrollPane();

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
        System.out.println("Route:" + DBroute);
        locationList = APPRoutes.getLeveringenByRoute(DBroute);

        addLocations();
    }

    private void addLocations() throws IOException {
        int id = 0;
        for (DBlevering location : locationList) {
            location.Print();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationInfo.fxml"));
            Parent content = loader.load();
            Route.add(content);

            LocationInfo controller = loader.getController();
            controller.setDBroute(location, id);
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
}
