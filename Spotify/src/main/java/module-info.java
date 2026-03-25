module spotify.spotify {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.groovy;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;

    opens spotify.spotify to javafx.fxml;
    opens spotify.spotify.controller to javafx.fxml;
    opens spotify.spotify.domain to javafx.base;
    exports spotify.spotify;
}