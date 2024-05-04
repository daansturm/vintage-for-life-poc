package VintageForLife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;

public class VintageForLifeAPP extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("Pictures/vintage for life.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
