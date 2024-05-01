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

    opens VintageForLife to javafx.fxml;
    exports VintageForLife;
    exports VintageForLife.API;
    opens VintageForLife.API to javafx.fxml;

}