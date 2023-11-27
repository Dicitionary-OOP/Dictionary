package dictionary.graphic.controllers;

import dictionary.base.api.TextToIpaAPI;
import dictionary.base.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class TextToIPAController {
    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    public void onIpaButtonClick() {
        if (!Utils.isNetworkConnected()) {
            Notifications.create()
                    .title("Dictionary")
                    .text("You are offline!")
                    .showWarning();
            return;
        }

        Thread thread = new Thread(() -> {
            String output = null;
            try {
                output = TextToIpaAPI.textToIPA(inputTextArea.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String finalOutput = output;
            Platform.runLater(() -> outputTextArea.setText(finalOutput));
        });
        thread.setDaemon(true);
        thread.start();

        outputTextArea.setPromptText("Dang phien am...");
        outputTextArea.setText(null);
    }
}
