package VintageForLife;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    static private Stage stage;
    static private Scene scene;
    static private Parent root;

    public void ShowLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ShowOverzichtRoutes(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("OverzichtRoutes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ShowRouteVisualisatie(ActionEvent event, int routeID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RouteVisualisatie.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        RouteVisualisatie controller = loader.getController();
        controller.setRouteID(routeID); // Stel het routeID in op de controller
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
