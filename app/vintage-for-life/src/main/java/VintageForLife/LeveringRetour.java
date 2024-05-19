package VintageForLife;

import VintageForLife.DB.DBroute;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.InputMethodEvent;

import java.util.ArrayList;
import java.util.List;


public class LeveringRetour {
    private LeveringRetourListener listener;

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

    @FXML
    private MenuButton change_route;

    private String route_id;
    private String id;


    @FXML
    void initialize() {
        change_route.getItems().clear();
    }

    private void MenuItemSelected(String TO, String From, String ID)
    {
        if (listener != null)
            listener.onLeveringRetourChange(TO, From, ID);

    }

    public void setMenuItems(List<DBroute> routes)
    {
        change_route.getItems().clear();
        List<MenuItem> items = new ArrayList<MenuItem>();
        for(DBroute route : routes)
        {
            if(!route.getId().equals(this.route_id)) {
                MenuItem item = new MenuItem("verplaats naar Route " + route.getId());
                item.setId(route.getId());
                item.setOnAction(event -> {
                    MenuItemSelected(route.getId(), this.route_id, this.id);
                    MenuItem source = (MenuItem) event.getSource();
                    System.out.println("MenuItem clicked: " + source.getText());
                });
                items.add(item);
            }

        }
        MenuItem item = new MenuItem("verplaats naar nieuwe route");
        item.setId("nieuw");
        item.setOnAction(event -> MenuItemSelected("nieuw", this.route_id, this.id));
        items.add(item);

        change_route.getItems().addAll(items);


    }

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

    public void setLeveringRetourListener(LeveringRetourListener listener) {
        this.listener = listener;
    }

    public void setRouteID(String route_id) {
        this.route_id = route_id;
    }

    public String getRouteId(){
        return route_id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
