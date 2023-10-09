module dictionary.graphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;
    requires org.json;

    requires dictionary.base;

    exports dictionary.graphic;

    opens dictionary.graphic to javafx.fxml;

    exports dictionary.graphic.controllers;

    opens dictionary.graphic.controllers to javafx.fxml;
}
