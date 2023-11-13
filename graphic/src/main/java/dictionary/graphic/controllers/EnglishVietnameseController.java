package dictionary.graphic.controllers;

import dictionary.base.api.TextToSpeechOfflineAPI;
import dictionary.base.database.Database;
import dictionary.base.database.DictionaryDatabase;
import dictionary.base.Example;
import dictionary.base.Explain;
import dictionary.base.Word;
import javafx.application.Platform;
import javafx.fxml.FXML;

import dictionary.base.Dictionary;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnglishVietnameseController {
    private Dictionary dictionary;
    @FXML
    private TextArea outputField;
    @FXML
    private Button speech;
    @FXML
    private Label wordField;
    @FXML
    private VBox explainField;
    @FXML
    private void initialize() {
        try {
            dictionary = new Dictionary();
            // Test show word
            showDetail("1");
        }catch (Exception e) {
            outputField.setText("Đã có lỗi với từ điển");
        }
    }

    private void showDetail(String wordID) {
        Thread thread = new Thread(() -> {
            try {
                Word word = dictionary.getDatabase().getWordByWordID(wordID);
                DictionaryDatabase database = dictionary.getDatabase();
                wordField.setText(word.getWord());
                ArrayList<Explain> explains = database.getExplainsByWordID(word.getWordID());
                for (Explain explain : explains) {
                    Label type = new Label(explain.getType() + "\n");
                    Label meaning = new Label("\t" + explain.getMeaning() + "\n");
                    explainField.getChildren().add(type);
                    explainField.getChildren().add(meaning);
                    ArrayList<Example> examples = database.getExamples(explain.getExplainID());
                    for (Example example : examples) {
                        Label exampleText = new Label("\t\t" + example.getExample() + "\n");
                        explainField.getChildren().add(exampleText);
                    }
                }
            } catch (SQLException e) {
                wordField.setText(null);
                explainField.getChildren().clear();
                Label error = new Label("Đã có lỗi xảy ra");
                explainField.getChildren().add(error);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    @FXML
    private void onPlayAudioButton(){
        Thread thread = new Thread(() -> TextToSpeechOfflineAPI.getTextToSpeech(wordField.getText()));
        thread.setDaemon(true);
        thread.start();
    }
}
