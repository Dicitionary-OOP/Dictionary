package dictionary.graphic.controllers;

import dictionary.base.api.TextToIpaAPI;
import dictionary.base.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class TextToIPAController {
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
        targetLanguageChoiceBox.setValue("en");
    }
    @FXML
    public void onSwitchSourceLanguage() {
        if (sourceLanguageChoiceBox.getValue().equals("vi"))  {
            targetLanguageChoiceBox.setValue("vi");
        } else{
            targetLanguageChoiceBox.setValue("en");
        }
    }
    @FXML
    public void onSwitchTargetLanguage() {
        if (targetLanguageChoiceBox.getValue().equals("vi")){
            sourceLanguageChoiceBox.setValue("vi");
        } else {
            sourceLanguageChoiceBox.setValue("en");
        }

    }
    @FXML
    public void onIpaButtonClick() throws IOException {


        outputTextArea.setPromptText("Đang phien am...");
        outputTextArea.setText(null);

        final Thread thread = new Thread(() -> {
            String output = null;
            try {
                output = TextToIpaAPI.textToIPA(inputTextArea.getText());
                final String finalOutput = output;
                Platform.runLater(() -> outputTextArea.setText(finalOutput));
            } catch(final IOException e) {
                e.printStackTrace();
                String errorMessage;
                if(!Utils.isNetworkConnected()) {
                    errorMessage = "Internet is not connect.";
                } else {
                    errorMessage = "Error.";
                }


                Platform.runLater(() -> outputTextArea.setText((errorMessage)));
            }

        });

        thread.setDaemon(true);
        thread.start();
    }


}
