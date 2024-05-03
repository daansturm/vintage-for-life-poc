package VintageForLife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VintageForLifeAPP extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Connection connection = null;
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/vintage_for_life_poc";
            String dbUser = "root";
            String dbPassword = "password";

            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loader.load();

        LoginScreen controller = loader.getController();
        controller.setConnection(connection);
     

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("Pictures/vintage for life.png"))));
        stage.setScene(scene);
        stage.show();
       

    }

    public static void main(String[] args) {
        launch();
    }
}
