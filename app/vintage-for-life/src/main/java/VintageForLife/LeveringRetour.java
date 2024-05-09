package VintageForLife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;




public class LeveringRetour {
    @FXML
    private Label LeveringRetourtxt;
    @FXML
    private Label plaatstxt;
    @FXML
    private Label adrestxt;
    @FXML
    private Label postcodetxt;
    @FXML
    private Label landtxt;

    private int id;




    // Methode om de label te retourneren
    public Label getLeveringLabel() {
        return LeveringRetourtxt;
    }

    public Label getPlaatstLabel() {
        return plaatstxt;
    }

    public Label getAdrestLabel() {
        return adrestxt;
    }

    public Label getLandLabel() {
        return landtxt;

    }

    public Label getPostcodetxt() {
        return postcodetxt;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
