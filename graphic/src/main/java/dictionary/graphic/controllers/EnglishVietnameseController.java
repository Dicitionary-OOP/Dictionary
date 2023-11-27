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
    private Button speech;
    @FXML
    private Label wordField;
    @FXML
    private Label pronounceField;
    @FXML
    private VBox explainField;

    @FXML
    private void initialize() {
        try {
            showDetail(Dictionary.getInstance().getAllWords().get(2).get(1));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDetail(String wordID) {
        Thread thread = new Thread(() -> {
            try {
                Word word = Dictionary.getInstance().getDatabase().getWordByWordID(wordID);
                DictionaryDatabase database = Dictionary.getInstance().getDatabase();

                wordField.setText(word.getWord());
                pronounceField.setText('[' + word.getPronunce() + ']');
                ArrayList<Explain> explains = database.getExplainsByWordID(word.getWordID());
                for (Explain explain : explains) {
                    Label type = new Label(explain.getType() + "\n");
                    Label meaning = new Label("\t" + explain.getMeaning() + "\n");
                    type.getStyleClass().add("level1");
                    type.setWrapText(true);
                    explainField.getChildren().add(type);
                    meaning.getStyleClass().add("level2");
                    meaning.setWrapText(true);
                    explainField.getChildren().add(meaning);
                    ArrayList<Example> examples = database.getExamples(explain.getExplainID());
                    for (Example example : examples) {
                        Label exampleText = new Label("\t\t" + example.getExample() + "\n");
                        exampleText.getStyleClass().add("level3");
                        exampleText.setWrapText(true);
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
