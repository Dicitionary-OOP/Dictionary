package dictionary.graphic.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import dictionary.graphic.Theme;

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
        languageChoiceBox.setValue("vi");

        ObservableList<String> themeNames = FXCollections.observableArrayList();
        for (Theme theme : Theme.values()) {
            themeNames.add(theme.name);
        }
        themeChoiceBox.setItems(themeNames);
        themeChoiceBox.setValue(SceneController.getInstance().getTheme().name());
    }

    @FXML
    private void applySettings() {
        Notifications.create()
            .title("Dictionary")
            .text("Settings have been updated")
            .showInformation();

        try {
            SceneController.getInstance().setLocale(languageChoiceBox.getValue());
            SceneController.getInstance().setTheme(themeChoiceBox.getValue());
            SceneController.getInstance().switchScene("/views/settings.fxml");
        } catch(Exception e){}
    }
}
