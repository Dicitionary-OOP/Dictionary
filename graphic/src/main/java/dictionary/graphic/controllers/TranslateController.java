package dictionary.graphic.controllers;

import dictionary.base.api.GoogleTranslateAPI;
import dictionary.base.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class TranslateController {
    @FXML
    private ChoiceBox<String> sourceLanguageChoiceBox;

    @FXML
    private ChoiceBox<String> targetLanguageChoiceBox;

    @FXML
    private SplitPane translatePane;

    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    private void initialize() {
        sourceLanguageChoiceBox.setItems(FXCollections.observableArrayList("en", "vi"));
        targetLanguageChoiceBox.setItems(FXCollections.observableArrayList("en", "vi"));
        sourceLanguageChoiceBox.setValue("en");
        targetLanguageChoiceBox.setValue("vi");
    }

    @FXML
    public void onTranslateButtonClick() throws IOException {
        outputTextArea.setPromptText("Đang dịch...");
        outputTextArea.setText(null);

        final Thread thread = new Thread(() -> {
            String output = null;
            try {
                output = GoogleTranslateAPI.translate(sourceLanguageChoiceBox.getValue(),
                        targetLanguageChoiceBox.getValue(), inputTextArea.getText());
                final String finalOutput = output;
                Platform.runLater(() -> outputTextArea.setText(finalOutput));
            } catch (final Exception e) {
                e.printStackTrace();
                String errorMessage;
                if (!Utils.isNetworkConnected()) {
                    errorMessage = "Internet is not connect.";
                } else {
                    errorMessage = "Error.";
                }
                Platform.runLater(() -> outputTextArea.setText(errorMessage));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void onSwitchSourceLanguage() {
        if (sourceLanguageChoiceBox.getValue().equals("en")) {
            targetLanguageChoiceBox.setValue("vi");
        } else {
            targetLanguageChoiceBox.setValue("en");
        }
    }

    @FXML
    public void onSwitchTargetLanguage() {
        if (targetLanguageChoiceBox.getValue().equals("en")) {
            sourceLanguageChoiceBox.setValue("vi");
        } else {
            sourceLanguageChoiceBox.setValue("en");
        }
    }
}
