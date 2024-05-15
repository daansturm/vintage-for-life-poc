package VintageForLife;

import VintageForLife.DB.DBConnection;
import VintageForLife.Routes.APPRoutes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class VintageForLifeAPP extends Application {

    private Connection connection;

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        connection = DBConnection.getConnection();
        APPRoutes.SQLRoutes();
        APPRoutes.SQLUnAssignedLeveringen();
        APPRoutes.SQLUnAssignedRetour();

        APPRoutes.MakeRoute();



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
