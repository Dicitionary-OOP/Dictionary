package dictionary.graphic.controllers;

import dictionary.base.api.SynonymAPI;
import dictionary.base.api.TextToIpaAPI;
import dictionary.base.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class SynonymController {
    @FXML
    private TextArea outputSynonym;
    @FXML
    private TextField searchSynonym;
    @FXML
    private Button search;

    @FXML
    private void onSynonymSearchClick() throws IOException {
        String input = searchSynonym.getText().trim();
        if(input == null){
            return;
        }

        outputSynonym.setText(null);
        outputSynonym.setPromptText("Finding ....");

        Thread thread = new Thread(() -> {
            ArrayList<String> output = null;
            String result = "";
            try {
                output = new ArrayList<String>();
                output = SynonymAPI.getSynonyms(input);
                for (String x : output) {
                    result += x + "\n";
                }

                if (result == "") {
                    result = "\t\t\t\t\t\t\t No word searched.\n \t\t\t\t\t\tPlease checking searchSynonym";
                }

                final String resultS = result;
                Platform.runLater(() -> outputSynonym.setText(resultS));
            } catch (Exception e) {
                e.printStackTrace();
                outputSynonym.setText(null);
                outputSynonym.setPromptText("Finding ....");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
