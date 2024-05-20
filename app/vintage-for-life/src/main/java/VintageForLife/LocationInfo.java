package VintageForLife;

import VintageForLife.DB.GraphhopperLocatie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LocationInfo {
    @FXML
    private Label route;

    @FXML
    private Label route_info;

    private int ID;

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
