package VintageForLife;

import VintageForLife.DB.DBConnection;
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

        String sql = "SELECT * FROM levering";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        ArrayList<Integer> idArray = new ArrayList<>();
        ArrayList<String> statusArray = new ArrayList<>();
        ArrayList<String> bezorgdatumArray = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String status = resultSet.getString("status");
                String bezorgdatum = resultSet.getString("bezorgdatum");

                idArray.add(id);
                statusArray.add(status);
                bezorgdatumArray.add(bezorgdatum);
            }



        for (int i = 0; i < idArray.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeveringRetour.fxml"));



                Parent content = loader.load();
                Levering.add(content);

                LeveringRetour controller = loader.getController();
                controller.getLeveringLabel().setText("Levering: " + i );




                } catch (IOException e) {
                e.printStackTrace();

            }
        }

        VBox items = new VBox(Leveringen.getWidth());
        items.getChildren().addAll(Levering);
        items.prefWidthProperty().bind(Leveringen.widthProperty());
        Leveringen.setContent(items);


    }

    ///todo julian
    void addInfoLevering(int id, LeveringRetour controller) throws SQLException {

        String sql =   "SELECT b.id AS bestelling_id, b.klant_id, b.status, b.installatieservice, b.straat, b.huisnummer, b.plaats, b.postcode, b.land, " +
                "k.voornaam, k.tussenvoegsel, k.achternaam, k.telefoonnummer " +
                "FROM bestelling b " +
                "INNER JOIN bestelling_levering bl ON b.id = bl.bestelling_id " +
                "INNER JOIN levering l ON bl.levering_id = l.id " +
                "INNER JOIN klanten k ON b.klant_id = k.id " +
                "WHERE l.status = 'nieuw' and l.id = ";

        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            int bestellingId = resultSet.getInt("bestelling_id");
            int klantId = resultSet.getInt("klant_id");
            String status = resultSet.getString("status");
            boolean installatieservice = resultSet.getBoolean("installatieservice");
            String straat = resultSet.getString("straat");
            String huisnummer = resultSet.getString("huisnummer");
            String plaats = resultSet.getString("plaats");
            String postcode = resultSet.getString("postcode");
            String land = resultSet.getString("land");

            // Klantinformatie
            String voornaam = resultSet.getString("voornaam");
            String tussenvoegsel = resultSet.getString("tussenvoegsel");
            String achternaam = resultSet.getString("achternaam");
            String telefoonnummer = resultSet.getString("telefoonnummer");



        }
    }









    @FXML
    void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }
}
