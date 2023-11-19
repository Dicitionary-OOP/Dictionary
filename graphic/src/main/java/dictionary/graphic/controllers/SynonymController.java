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
    private TextField searchSynonym, limitSynonym;
    @FXML
    private Button search;

    @FXML
    private void onSynonymSearchClick() throws IOException {
        outputSynonym.setText(null);
        outputSynonym.setPromptText("Finding ....");

        Thread thread = new Thread(() -> {
            ArrayList<String> output = null;
            String result = "";
            try {
                int limited = Integer.parseInt(limitSynonym.getText());
                output = new ArrayList<String>();
                output = SynonymAPI.getSynonyms(searchSynonym.getText(), limited);
                for (String x : output) {
                    result += x + "\n";
                }
                final String resultS = result;
                Platform.runLater(() -> outputSynonym.setText(resultS));
            } catch (Exception e) {
                e.printStackTrace();
                String errorMessage;
                outputSynonym.setText(null);
                outputSynonym.setPromptText("Finding ....");
                if(!Utils.isNetworkConnected()) {
                    errorMessage = "Internet is not connect.";
                } else {
                    errorMessage = "\t\t\t\t\t WARNING...  :  You need enter enough information. \n \t\t\t\t\t\tChecking Limit and searchSynonym";
                }
                Platform.runLater(() -> outputSynonym.setText(errorMessage));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
