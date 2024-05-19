package VintageForLife;


import VintageForLife.DB.DBlevering;
import VintageForLife.DB.DBroute;
import VintageForLife.DB.GraphhopperLocatie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LocationInfo {
    @FXML
    private BorderPane borderpane;

    @FXML
    private Label route;

    @FXML
    private Label route_info;

    private int ID;
    private SceneController Scene = new SceneController();
    private DBlevering dBroute = new DBlevering();

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDBroute(GraphhopperLocatie location, int id ) {
        this.route.setText(location.getAdres().getStraat() + " " + location.getAdres().getHuisnummer() + " " + location.getAdres().getPlaats());
        this.ID = id;
    }

    public void setRouteInfo(String info) {
        this.route_info.setText(info);
    }
}
