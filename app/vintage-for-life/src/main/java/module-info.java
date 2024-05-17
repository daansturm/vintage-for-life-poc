module VintageForLife {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires java.sql;
    requires org.json;
    requires jbcrypt;

    opens VintageForLife to javafx.fxml;
    exports VintageForLife;

    opens VintageForLife.API to javafx.fxml;
    exports VintageForLife.API;
    exports VintageForLife.DB;
    opens VintageForLife.DB to javafx.fxml;
    exports VintageForLife.Routes;
    opens VintageForLife.Routes to javafx.fxml;


}