package dictionary.graphic.controllers;

import dictionary.base.externalApi.GoogleTranslateApi;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class TranslateController extends MenuController {
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
                output = GoogleTranslateApi.translate(sourceLanguageChoiceBox.getValue(),
                        targetLanguageChoiceBox.getValue(), inputTextArea.getText());
                final String finalOutput = output;
                Platform.runLater(() -> outputTextArea.setText(finalOutput));
            } catch (final IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> outputTextArea.setText("Error"));
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}
