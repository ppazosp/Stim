module com.bdii.stimfx.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.postgresql.jdbc;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;

    opens com.bdii.stimfx.gui to javafx.fxml;
    exports com.bdii.stimfx.gui;
    exports com.bdii.stimfx.aplicacion;
    exports com.bdii.stimfx.baseDatos;
}