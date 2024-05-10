package VintageForLife;


import VintageForLife.DB.DBroute;
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

public class RouteInfo {
   private RouteListener listener;

    @FXML
    private BorderPane borderpane;

    @FXML
    private Label route;

    @FXML
    private Label route_info;

    private int ID;
    private SceneController Scene = new SceneController();
    private DBroute dBroute = new DBroute();

    @FXML
    void Select(MouseEvent event) {

        if (listener != null)
            listener.onRouteSelected(this.ID);

        setSelected();

    }

    @FXML
    void ShowVisualisation(ActionEvent event) throws IOException {
        System.out.println(this.ID);
        Scene.ShowRouteVisualisatie(event, this.ID);

    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRouteSelectionListener(RouteListener listener) {
        this.listener = listener;
    }

    public void setDBroute(DBroute route) {
        this.dBroute = route;
        this.route.setText("Route: " + dBroute.getId());
        this.route_info.setText(dBroute.getRoute_info());
        this.ID = Integer.parseInt(route.getId());
    }


    public void setSelected()
    {
        BackgroundFill background_fill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        borderpane.setBackground(background);
    }

    public void setUnseleced()
    {
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        borderpane.setBackground(background);
    }

    public void getID(int ID) {
        this.ID = ID;
    }

}
