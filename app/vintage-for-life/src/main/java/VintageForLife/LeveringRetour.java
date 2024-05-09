package VintageForLife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;




public class LeveringRetour {
    @FXML
    private Label LeveringRetourtxt;
    @FXML
    private Label plaatstxt;


    // Methode om de label te retourneren
    public Label getLeveringLabel() {
        return LeveringRetourtxt;
    }

    public Label getPlaatstLabel() {
        return plaatstxt;
    }

}
