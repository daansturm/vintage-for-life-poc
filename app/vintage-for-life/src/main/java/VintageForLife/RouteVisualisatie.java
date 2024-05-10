package VintageForLife;

import VintageForLife.DB.DBroute;
import VintageForLife.DB.GraphhopperLocatie;
import VintageForLife.Routes.APPRoutes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteVisualisatie {

    private SceneController Scene = new SceneController();



    @FXML
    private Label route;

    @FXML
    private Label route_info;

    @FXML
    private ScrollPane locaties = new ScrollPane();

    List<DBroute> routeList = new ArrayList<>();
    private int routeID = -1;





    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public void initialize() {
        routeList = APPRoutes.getRoutes();

        if (routeList.isEmpty() || routeID == -1 || routeID > routeList.size())  {
            return;
        }
        DBroute route = routeList.get(routeID);

        for (GraphhopperLocatie locatie : route.getLocaties())
        {

        }

    }


    @FXML
    private void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }

    @FXML
    void Routes(ActionEvent event) throws IOException {
        Scene.ShowOverzichtRoutes(event);

    }


}
