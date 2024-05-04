package VintageForLife;

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


public class OverzichtRoutes {
    @FXML
    private ScrollPane Leveringen = new ScrollPane();
    @FXML
    private ScrollPane Retouren;
    @FXML
    private ScrollPane Routes;
    private SceneController Scene = new SceneController();


    private Stage stage;
    private Scene scene;
    private Parent root;

    List<Node> Levering = new ArrayList<>();

    @FXML
    private void initialize() {
        // Voeg inhoud toe aan de ScrollPane nadat de pagina is geladen
        addLeveringen();
    }

    public void addLeveringen() {
        for (int i = 0; i < 3; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeveringRetour.fxml"));
                Parent content = loader.load();
                Levering.add(content);
                //((VBox) Leveringen.getContent()).getChildren().add(content);
            } catch (IOException e) {
                e.printStackTrace();
                // Behandel de fout
            }
        }

        VBox items = new VBox(Leveringen.getWidth());
        items.getChildren().addAll(Levering);
        items.prefWidthProperty().bind(Leveringen.widthProperty());
        Leveringen.setContent(items);


    }





    public OverzichtRoutes() throws IOException {

        System.out.println(getClass().getResource("LeveringRetour.fxml"));
      }




    @FXML
    void Logout(ActionEvent event) throws IOException {
        Scene.ShowLoginScreen(event);

    }
}
