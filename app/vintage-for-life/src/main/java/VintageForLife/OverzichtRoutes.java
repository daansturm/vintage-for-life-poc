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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OverzichtRoutes implements RouteListener, LeveringRetourListener{

    private final Connection connection;
    private SceneController Scene = new SceneController();

    @FXML
    private ScrollPane Leveringen;
    @FXML
    private ScrollPane Retouren;
    @FXML
    private ScrollPane Routes ;



    private Stage stage;
    private Scene scene;
    private Parent root;


    List<RouteInfo> routeInfoList = new ArrayList<>();
    List<DBroute> routeList = new ArrayList<>();


    VBox route = new VBox();
    List<VBox> routeLevering = new ArrayList<>();
    List<VBox> routeRetour = new ArrayList<>();

    List<Node> Route = new ArrayList<>();
    List<Node> Retour = new ArrayList<>();
    List<Node> Levering = new ArrayList<>();

    public OverzichtRoutes() throws IOException {

        connection = DBConnection.getConnection();

    }

    @FXML
    private void initialize() throws SQLException, IOException {
        // Voeg inhoud toe aan de ScrollPane nadat de pagina is geladen

        routeList = APPRoutes.getRoutes();



        addRoutes();
        addLeveringen();
        addRetour();

        if (!routeInfoList.isEmpty()) {
            route.prefWidthProperty().bind(Routes.widthProperty());
            Routes.setContent(route);
            routeInfoList.get(0).setSelected();
        }

        if(!routeRetour.isEmpty()) {
            routeRetour.get(0).prefWidthProperty().bind(Retouren.widthProperty());
            Retouren.setContent(routeRetour.get(0));
        }

        if(!routeLevering.isEmpty()) {
            routeLevering.get(0).prefWidthProperty().bind(Leveringen.widthProperty());
            Leveringen.setContent(routeLevering.get(0));
        }


    }


    private void addRoutes() throws IOException {
        int id = 0;
        Route.clear();
        route.getChildren().clear();

        for (DBroute route : routeList) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RouteInfo.fxml"));
            Parent content = loader.load();
            Route.add(content);

            RouteInfo controller = loader.getController();
            controller.setRouteSelectionListener(this);
            controller.setDBroute(route, id);
            routeInfoList.add(controller);
            id++;

            //((VBox) Leveringen.getContent()).getChildren().add(content);
        }
            route.getChildren().addAll(Route);



    }


    private void addLeveringen() throws IOException {

        routeLevering.clear();
        for(DBroute route : routeList)
        {
            Levering.clear();
            VBox _routeLevering = new VBox();
            int i = 0;
            for(DBlevering levering : route.getLeveringen())
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeveringRetour.fxml"));
                Parent content = loader.load();

                content.setVisible(true);
                Levering.add(content);

                DBadres adres = levering.getFirstAdres();
                LeveringRetour controller = loader.getController();
                controller.setID("L" + levering.getId());
                controller.setRouteID(route.getId());
                controller.setLeveringRetourListener(this);
                controller.getLeveringLabel().setText("Levering: " + i);
                controller.getPlaatstLabel().setText("Plaats: " + adres.getPlaats() );
                controller.getAdrestLabel().setText("adres: " + adres.getStraat() + " " +adres.getHuisnummer());
                controller.getPostcodetxt().setText("Postcode: " + adres.getPostcode());
                controller.getLandLabel().setText("Land: " + adres.getLand() );
                controller.setMenuItems(routeList);


                i++;
            }
            _routeLevering.getChildren().addAll(Levering);
            routeLevering.add(_routeLevering);

        }




    }

    private void addRetour() throws IOException {
        routeRetour.clear();


        for(DBroute route : routeList)
        {
            Retour.clear();
            VBox _routeRetour = new VBox();
            int i = 0;
            for(DBretour retour : route.getRetouren())
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeveringRetour.fxml"));
                Parent content = loader.load();

                content.setVisible(true);
                Retour.add(content);

                DBadres adres = retour.getAdres();
                LeveringRetour controller = loader.getController();
                controller.setID("R" + retour.getId());
                controller.setRouteID(route.getId());
                controller.setLeveringRetourListener(this);
                controller.getLeveringLabel().setText("Retour: " + i);
                controller.getPlaatstLabel().setText("Plaats: " + adres.getPlaats() );
                controller.getAdrestLabel().setText("adres: " + adres.getStraat() + " " +adres.getHuisnummer());
                controller.getPostcodetxt().setText("Postcode: " + adres.getPostcode());
                controller.getLandLabel().setText("Land: " + adres.getLand() );
                controller.setMenuItems(routeList);

                i++;
            }


            _routeRetour.getChildren().addAll(Retour);
            routeRetour.add(_routeRetour);

        }


        //VBox items = new VBox(Leveringen.getWidth());
        //items.getChildren().addAll(Levering);
        //items.prefWidthProperty().bind(Leveringen.widthProperty());
        //Leveringen.setContent(items);


    }




    public void onRouteSelected(int ID) {
        // Logica om de achtergrond te verwijderen
        for (int i = 0; i < routeInfoList.size(); i++) {
            routeInfoList.get(i).setUnseleced();


        }
        if (!routeLevering.isEmpty() && ID < routeLevering.size()) {
            routeLevering.get(ID).prefWidthProperty().bind(Leveringen.widthProperty());
            Leveringen.setContent(routeLevering.get(ID));
        }

        if (!routeRetour.isEmpty() && ID < routeRetour.size()) {
            routeRetour.get(ID).prefWidthProperty().bind(Retouren.widthProperty());
            Retouren.setContent(routeRetour.get(ID));
        }
    }

    public void onLeveringRetourChange(String TO, String From, String ID) {

            DBroute ToRoute = null;
            DBroute FromRoute = null;

            for(DBroute route : routeList)
            {
                if (route.getId().equals(TO))
                    ToRoute = route;
                if(route.getId().equals(From))
                    FromRoute = route;
            }



            if (TO.equals("nieuw"))
            {
                ToRoute = new DBroute(LocalDate.now());
                try {
                    ToRoute.setBeginadres(DBConnection.getSQLBeginEindAdress(1));
                    ToRoute.setEindadres(DBConnection.getSQLBeginEindAdress(1));
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }



            if(ToRoute == null || FromRoute == null)
                return;


            if(ID.contains("L"))
            {
                DBlevering levering = FromRoute.getLeveringEnVerwijder(ID.replace("L", ""));
                levering.Print();
                if (levering == null)
                    return;
                ToRoute.voegLeveringToe(levering);

            }

            if(ID.contains("R"))
            {
                DBretour retour = FromRoute.getRetourEnVerwijder(ID.replace("R", ""));
                retour.Print();
                if (retour == null)
                    return;
                ToRoute.voegRetourToe(retour);

            }



            ToRoute.MaakGraphhopperList();
            FromRoute.MaakGraphhopperList();
            try {
                FromRoute.setRoute(DBConnection.setSQLRoute(FromRoute));
                ToRoute.setRoute(DBConnection.setSQLRoute(ToRoute));
                APPRoutes.SQLRoutes();
                routeList = APPRoutes.getRoutes();

                addRoutes();
                addLeveringen();
                addRetour();
            }
            catch (SQLException | IOException e) {
                e.printStackTrace();
            }








        }



    @FXML
    void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }
    @FXML
    void Routes(ActionEvent event) throws IOException {
        Scene.ShowOverzichtRoutes(event);

    }
}
