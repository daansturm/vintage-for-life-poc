package VintageForLife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class RouteVisualisatie {

    private SceneController Scene = new SceneController();
    @FXML
    void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }
}
