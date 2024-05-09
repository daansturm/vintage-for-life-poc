package VintageForLife;

import VintageForLife.DB.*;
import VintageForLife.Routes.APPRoutes;
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

public class OverzichtRoutes implements RouteListener{

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


    List<RouteInfo> routeInfoList = new ArrayList<>();
    List<DBroute> routeList = new ArrayList<>();
    List<DBlevering> leveringList = new ArrayList<>();

    List<Node> Levering = new ArrayList<>();
    List<Node> Retour = new ArrayList<>();
    List<Node> Route = new ArrayList<>();

    public OverzichtRoutes() throws IOException {

        connection = DBConnection.getConnection();

    }

    @FXML
    private void initialize() throws SQLException, IOException {
        // Voeg inhoud toe aan de ScrollPane nadat de pagina is geladen
        APPRoutes.SQLRoutes();
        APPRoutes.SQLUnAssignedLeveringen();
        routeList = APPRoutes.getRoutes();
        leveringList = APPRoutes.getUnAssignedLevering();

        addRoutes();
        addLeveringen();
    }


    public void addRoutes()
    {
        for (int i = 0; i < routeList.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RouteInfo.fxml"));
                Parent content = loader.load();
                Route.add(content);

                RouteInfo controller = loader.getController();
                routeInfoList.add(controller);
                controller.setRouteSelectionListener(this);

                //((VBox) Leveringen.getContent()).getChildren().add(content);
            } catch (IOException e) {
                e.printStackTrace();
                // Behandel de fout
            }

            VBox items = new VBox(Routes.getWidth());
            items.getChildren().addAll(Route);
            items.prefWidthProperty().bind(Routes.widthProperty());
            Routes.setContent(items);

            if (routeInfoList != null)
                routeInfoList.get(0).setSelected();
        }
    }


    public void addLeveringen() throws SQLException, IOException {

        for(DBroute route : routeList)
        {
            int i = 0;
            for(DBlevering levering : route.getLeveringen())
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeveringRetour.fxml"));
                Parent content = loader.load();
                content.setVisible(false);
                Levering.add(content);

                DBadres adres = levering.getFirstAdres();
                LeveringRetour controller = loader.getController();
                controller.getLeveringLabel().setText("Levering: " + i);
                controller.getPlaatstLabel().setText("Plaats: " + adres.getPlaats() );
                controller.getAdrestLabel().setText("adres: " + adres.getStraat() + " " +adres.getHuisnummer());
                controller.getPostcodetxt().setText("Postcode: " + adres.getPostcode());
                controller.getLandLabel().setText("Land: " + adres.getLand() );

                i++;
            }
        }
        VBox items = new VBox(Leveringen.getWidth());
        items.getChildren().addAll(Levering);
        items.prefWidthProperty().bind(Leveringen.widthProperty());
        Leveringen.setContent(items);


    }


    public void onRouteSelected() {
        // Logica om de achtergrond te verwijderen
        for(int i = 0; i < routeInfoList.size(); i++)
        {
            routeInfoList.get(i).setUnseleced();

        }
    }









    @FXML
    void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }
}
