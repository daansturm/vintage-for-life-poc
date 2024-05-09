package VintageForLife;


import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class RouteInfo {
   private RouteListener listener;

    @FXML
    private BorderPane borderpane;

    @FXML
    private Label route;

    @FXML
    private Label route_info;

    @FXML
    void Select(MouseEvent event) {

        if (listener != null)
            listener.onRouteSelected();

        setSelected();




    }

    public void setRouteSelectionListener(RouteListener listener) {
        this.listener = listener;
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

}
