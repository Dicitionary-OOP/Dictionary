package dictionary.graphic.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import dictionary.graphic.Theme;
import dictionary.graphic.Font;
import dictionary.graphic.SettingsManager;
import java.util.Properties;

public class SettingsController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    @FXML
    private ChoiceBox<String> fontChoiceBox;

    @FXML
    private Button applyButton;

    private Properties settings;

    @FXML
    private void initialize() {
        settings = SettingsManager.loadSettings();

        languageChoiceBox.setItems(FXCollections.observableArrayList("en", "vi"));
        languageChoiceBox.setValue("vi");

        ObservableList<String> themeNames = FXCollections.observableArrayList();
        for (Theme theme : Theme.values()) {
            themeNames.add(theme.name());
        }
        themeChoiceBox.setItems(themeNames);
        themeChoiceBox.setValue(SceneController.getInstance().getTheme().name());

        ObservableList<String> fontNames = FXCollections.observableArrayList();
        for (Font font : Font.values()) {
            fontNames.add(font.name());
        }

        fontChoiceBox.setItems(fontNames);
        fontChoiceBox.setValue(SceneController.getInstance().getFont().name());

    }

    @FXML
    private void applySettings() {
        try {
            final String language = languageChoiceBox.getValue();
            final String theme = themeChoiceBox.getValue();
            final String font = fontChoiceBox.getValue();

            SceneController.getInstance().setLocale(language);
            SceneController.getInstance().setTheme(Theme.getTheme(theme));
            SceneController.getInstance().setFont(Font.getFont(font));

            settings.setProperty("language", language);
            settings.setProperty("theme", theme);
            settings.setProperty("font", font);
            SettingsManager.saveSettings(settings);

            Notifications.create()
            .title("Dictionary")
            .text("Settings have been updated")
            .showInformation();
        } catch(Exception e){}
    }
}
