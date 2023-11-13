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
    private SplitPane translatePane;

    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    public void onIpaButtonClick() throws IOException {
        outputTextArea.setPromptText("Dang phien am...");
        outputTextArea.setText(null);

        if(!Utils.isNetworkConnected()) {
            Notifications.create()
                .title("Dictionary")
                .text("You are offline!")
                .showWarning();
            return;
        }        

        final Thread thread = new Thread(() -> {
            Platform.runLater(() -> outputTextArea.setText(
                TextToIpaAPI.textToIPA(inputTextArea.getText())
            ));
        });

        thread.setDaemon(true);
        thread.start();
    }
}
