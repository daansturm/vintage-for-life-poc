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
    private DBroute route;

    @FXML
    private Label route_info;

    @FXML
    private ScrollPane locaties = new ScrollPane();

    private int routeID = -1;




    public void setRoute(DBroute route, int id) {
        this.routeID = id;
        this.route = route;
    }

    public void initialize() {

       // for (GraphhopperLocatie locatie : route.getLocaties())
     //   {

     //   }

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
