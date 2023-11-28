package dictionary.graphic.controllers;

import dictionary.base.api.TextToSpeechOfflineAPI;
import dictionary.base.database.DictionaryDatabase;
import dictionary.base.Example;
import dictionary.base.Explain;
import dictionary.base.Word;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import dictionary.base.Dictionary;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EnglishVietnameseController {
    @FXML
    private Button speech;
    @FXML
    private Label wordField;
    @FXML
    private Label pronounceField;
    @FXML
    private VBox explainField;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> suggestedWords;

    private ExecutorService searchWordExecutor = Executors.newSingleThreadExecutor();

    private Future searchWordFuture = null;

    @FXML
    private void initialize() {
        suggestedWords.setCellFactory((ListView<String> list) -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell.setOnMouseClicked((MouseEvent event) -> {
                pickCurrentSuggestion();
            });

            cell.setOnTouchReleased((TouchEvent event) -> {
                pickCurrentSuggestion();
            });

            return cell;
        });

        suggestedWords.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER:
                    pickCurrentSuggestion();
                    break;

                case UP:
                case KP_UP:
                    if (suggestedWords.getSelectionModel().getSelectedIndex() == 0) {
                        focusOnSearchBar();
                        suggestedWords.getSelectionModel().clearSelection();
                    }
                    break;
            }
        });
    }

    private void showDetail(Word word) {
        try {
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
    }
    @FXML
    private void onPlayAudioButton(){
        Thread thread = new Thread(() -> TextToSpeechOfflineAPI.getTextToSpeech(wordField.getText()));
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void onSearchBarInputTextChanged() {
        updateSuggestions();
    }

    @FXML
    private void onSearchBarKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                pickBestMatch();
                break;

            case DOWN:
            case KP_DOWN:
                useSuggestions();
                break;
        }
    }

    /**
     * Updates word suggestions.
     */
    private void updateSuggestions() {
        if (searchWordFuture != null && !searchWordFuture.isDone()) {
            searchWordFuture.cancel(true);
        }
        final String searchString = searchBar.getText();
        searchWordFuture = searchWordExecutor.submit(() -> {
            try {
                DictionaryDatabase database = Dictionary.getInstance().getDatabase();
                List<String> words = database.getWordsStartWith(searchString);
                Platform.runLater(() -> {
                    suggestedWords.setItems(
                            FXCollections.observableList(words)
                    );
                });
            } catch (SQLException e) {
                Platform.runLater(() -> handleSQLException(e));
            }
        });
    }

    /**
     * This function gets called when the user no longer
     * types in the word manually, and instead wants to
     * select one of the suggestions.
     */
    private void useSuggestions() {
        if (suggestedWords.getItems().isEmpty()) {
            return;
        }
        suggestedWords.requestFocus();
        suggestedWords.getSelectionModel().select(0);
    }

    /**
     * Loads the currently-selected suggestion into the
     * search bar, and display its word's details.
     */
    private void pickCurrentSuggestion() {
        String wordString = suggestedWords.getSelectionModel().getSelectedItem();

        try {
            DictionaryDatabase database = Dictionary.getInstance().getDatabase();
            Word word = database.getWordObjectByWord(wordString);
            showDetail(word);
        } catch (SQLException e) {
            handleSQLException(e);
        }

        searchBar.setText(wordString);
        focusOnSearchBar();
    }

    /**
     * Shows details of the best-match word, i.e. the word that is on top
     * of search results (suggestions).
     */
    private void pickBestMatch() {
        if (suggestedWords.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(String.format("Không tìm thấy từ tương tự \'%s\'", searchBar.getText()));
            alert.setContentText("Vui lòng nhập lại từ đúng hoặc thêm từ mới.");
            alert.showAndWait();
            return;
        }
        suggestedWords.getSelectionModel().select(0);
        pickCurrentSuggestion();
    }

    private void focusOnSearchBar() {
        searchBar.requestFocus();
        searchBar.deselect();
        searchBar.positionCaret(searchBar.getText().length());
    }

    private static void handleSQLException(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Không thể truy cập dữ liệu");
        alert.setContentText("Vui lòng thử lại sau");
        alert.showAndWait();
    }
}
