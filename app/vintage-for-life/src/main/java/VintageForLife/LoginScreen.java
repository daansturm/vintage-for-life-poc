package VintageForLife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginScreen  {
    private SceneController Scene = new SceneController();
    @FXML
    void Login(ActionEvent event) throws IOException {
        Scene.ShowOverzichtRoutes(event);

    }

}
