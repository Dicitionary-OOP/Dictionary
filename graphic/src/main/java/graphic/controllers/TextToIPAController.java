package graphic.controllers;

import org.controlsfx.control.Notifications;

import base.api.TextToIpaAPI;
import base.api.TextToSpeechOfflineAPI;
import base.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;

public class TextToIPAController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    public void onIpaButtonClick() {
        outputTextArea.setPromptText("Dang phien am...");
        outputTextArea.setText(null);

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
    }

    private void copyToClipBoard(String text) {
        if(text.isEmpty()) {
            return;
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
        Notifications.create()
                .owner(rootPane)
                .title("Dictionary")
                .text("Copied to clipboard!")
                .showInformation();
    }

    @FXML
    private void copySource() {
        copyToClipBoard(inputTextArea.getText());
    }

    @FXML
    private void copyTarget() {
        copyToClipBoard(outputTextArea.getText());
    }

    @FXML
    private void speakSource() {
        final Thread thread = new Thread(() -> {
            TextToSpeechOfflineAPI.getTextToSpeech(inputTextArea.getText());
        });
        thread.setDaemon(true);
        thread.start();
    }
}
