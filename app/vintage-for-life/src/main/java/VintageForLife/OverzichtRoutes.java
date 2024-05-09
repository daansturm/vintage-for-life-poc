package VintageForLife;

import VintageForLife.DB.DBConnection;
import VintageForLife.DB.DBadres;
import VintageForLife.DB.DBlevering;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OverzichtRoutes {

    private final Connection connection;
    private SceneController Scene = new SceneController();

    @FXML
    private ScrollPane Leveringen = new ScrollPane();
    @FXML
    private ScrollPane Retouren = new ScrollPane();;
    @FXML
    private ScrollPane Routes = new ScrollPane();


    private Stage stage;
    private Scene scene;
    private Parent root;

    List<Node> Levering = new ArrayList<>();
    List<Node> Retour = new ArrayList<>();
    List<Node> Route = new ArrayList<>();

    public OverzichtRoutes() throws IOException {

        connection = DBConnection.getConnection();

    }

    @FXML
    private void initialize() throws SQLException {
        // Voeg inhoud toe aan de ScrollPane nadat de pagina is geladen

        addRoutes();
        addLeveringen();
    }

    public void addRoutes()
    {


        for (int i = 0; i < 3; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RouteInfo.fxml"));
                Parent content = loader.load();
                Route.add(content);
                //((VBox) Leveringen.getContent()).getChildren().add(content);
            } catch (IOException e) {
                e.printStackTrace();
                // Behandel de fout
            }

            VBox items = new VBox(Routes.getWidth());
            items.getChildren().addAll(Route);
            items.prefWidthProperty().bind(Routes.widthProperty());
            Routes.setContent(items);
        }
    }


    public void addLeveringen() throws SQLException {

        List<DBlevering> DB_leveringen = DBConnection.getSQLDBlevering();
        for (int i = 0; i < DB_leveringen.size(); i++) {
             DB_leveringen.get(i).setLevering(DBConnection.getSQLDBbestelling(DB_leveringen.get(i)));
             for (int t = 0; t < DB_leveringen.get(i).getBestellingCount(); t++)
             {
                 DB_leveringen.get(i).getBestelling(t).setBestelling(DBConnection.getSQLDBproduct(DB_leveringen.get(i).getBestelling(t)));
             }

        }

        for (int i = 0; i < DB_leveringen.size(); i++) {
            try {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeveringRetour.fxml"));
                DBadres adres = DB_leveringen.get(i).getFirstAdres();



                Parent content = loader.load();
                Levering.add(content);

                LeveringRetour controller = loader.getController();
                controller.getLeveringLabel().setText("Levering: " + i );
                controller.getPlaatstLabel().setText("Plaats: " + adres.getPlaats() );




                } catch (IOException e) {
                e.printStackTrace();

            }
        }

        VBox items = new VBox(Leveringen.getWidth());
        items.getChildren().addAll(Levering);
        items.prefWidthProperty().bind(Leveringen.widthProperty());
        Leveringen.setContent(items);


    }












    @FXML
    void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }
}
