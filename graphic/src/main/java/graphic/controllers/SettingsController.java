package graphic.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

import graphic.enums.Theme;
import graphic.enums.Font;
import graphic.managers.SettingsManager;
import javafx.scene.control.TextField;

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
    private TextField chatGPTApiKey;

    @FXML
    private Button applyButton;

    @FXML
    private void initialize() {
        languageChoiceBox.setItems(FXCollections.observableArrayList("en", "vi"));
        languageChoiceBox.setValue(SceneController.getInstance().getLocale().getLanguage());

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
        chatGPTApiKey.setText(SettingsManager.getInstance().getProperty("chatgpt_api_key"));
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

            SettingsManager.getInstance().setProperty("language", language);
            SettingsManager.getInstance().setProperty("theme", theme);
            SettingsManager.getInstance().setProperty("font", font);
            SettingsManager.getInstance().setProperty("chatgpt_api_key", chatGPTApiKey.getText());
            SettingsManager.getInstance().saveSettings();

            Notifications.create()
                    .owner(rootPane)
                    .title("Dictionary")
                    .text("Settings have been updated")
                    .showInformation();
        } catch (Exception e) {
        }
    }
}
