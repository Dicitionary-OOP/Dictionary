package dictionary.graphic.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class SettingsController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    @FXML
    private Button applyButton;

    @FXML
    private void initialize() {
        languageChoiceBox.setItems(FXCollections.observableArrayList("en", "vi"));
        themeChoiceBox.setItems(FXCollections.observableArrayList("light", "dark"));
        languageChoiceBox.setValue("vi");
        themeChoiceBox.setValue("light");
    }

    @FXML
    private void applySettings() {
        Notifications.create()
            .title("Dictionary")
            .text("Settings have been updated")
            .showInformation();

        try {
            SceneController.getInstance().setLocale(languageChoiceBox.getValue());
            SceneController.getInstance().switchScene("/views/settings.fxml");
        } catch(Exception e){}
    }
}
