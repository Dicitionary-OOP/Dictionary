package dictionary.graphic.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.math3.util.Pair;
import org.controlsfx.control.Notifications;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dictionary.base.Dictionary;
import dictionary.base.Example;
import dictionary.base.Explain;
import dictionary.base.Word;
import dictionary.base.api.SpeechToTextOfflineAPI;
import dictionary.base.api.SpeechToTextOnlineAPI;
import dictionary.base.api.TextToSpeechOfflineAPI;
import dictionary.base.database.DictionaryDatabase;
import dictionary.base.utils.RecordManager;
import dictionary.base.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EnglishVietnameseController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button speech;

    @FXML
    private Button deleteWord;

    @FXML
    private Button speechToTextButton;

    @FXML
    private Label wordField;

    @FXML
    private Label pronounceField;

    @FXML
    private VBox explainField;

    @FXML
    private TextField searchBar;

    private Thread recordThread;

    private Thread speechToTextThread;

    @FXML
    private ListView<Pair<String, String>> suggestedWords;

    private final ExecutorService searchWordExecutor = Executors.newSingleThreadExecutor();

    private Future<?> searchWordFuture = null;

    private LazyLoadManager lazyLoadManager;

    private FontAwesomeIconView speechToTextIcon;

    private Boolean isProcessingSpeechToText = false;

    private final ResourceBundle bundle = ResourceBundle.getBundle("languages.language",
            SceneController.getInstance().getLocale());

    private final String speechFile = "speechToText.mp3";

    @FXML
    private void initialize() {
        // suggestedWords is invalid until this function is called.
        // Therefore, we don't instantiate LazyLoadManager in the
        // constructor nor in its in-class definition, but here.
        lazyLoadManager = new LazyLoadManager(suggestedWords);

        suggestedWords.setCellFactory((final ListView<Pair<String, String>> list) -> {
            final ListCell<Pair<String, String>> cell = new ListCell<>() {
                @Override
                public void updateItem(final Pair<String, String> p, final boolean empty) {
                    super.updateItem(p, empty);
                    if (!empty) {
                        setText(p.getFirst());
                    } else {
                        setText("");
                    }
                }
            };

            cell.setOnMouseClicked((final MouseEvent event) -> {
                pickCurrentSuggestion();
            });

            cell.setOnTouchReleased((final TouchEvent event) -> {
                pickCurrentSuggestion();
            });

            return cell;
        });

        suggestedWords.setOnKeyPressed((final KeyEvent event) -> {
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

                case DOWN:
                case KP_DOWN:
                    if (suggestedWords.getSelectionModel().getSelectedIndex() == suggestedWords.getItems().size() - 1) {
                        lazyLoadManager.loadAllSuggestionsFromGUIThread();
                    }
            }
        });

        suggestedWords.addEventHandler(ScrollEvent.SCROLL, (final ScrollEvent event) -> {
            // suggestedWords is a ListView object, which, under our investigation,
            // seems to capture ALL mouse scroll events and handle them itself
            // internally. However, when the scrolling exceeds boundary (i.e. when
            // the user continue to scroll up when the list is already at top, or
            // when the user continue to scroll down when the list is already at
            // bottom), the scroll event is no longer handled as such, and instead,
            // we could capture the event here. By getting deltaY, we can tell whether
            // we are at the top or bottom of the list. A negative deltaY tells us
            // that the list is reached bottom, so lazy-loading should be performed
            // then.
            if (event.getDeltaY() < 0) {
                lazyLoadManager.loadAllSuggestionsFromGUIThread();
            }
        });
    }

    private void showDetail(final String wordID) {
        final Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm delete");
        confirmationDialog.setHeaderText("Confirm Action");
        confirmationDialog.setContentText("Are you sure to delete this word?");

        try {
            final DictionaryDatabase database = Dictionary.getInstance().getDatabase();
            final Word word = database.getWordByWordID(wordID);
            explainField.getChildren().clear();
            speech.setDisable(false);
            deleteWord.setDisable(false);
            speech.setVisible(true);
            deleteWord.setVisible(true);

            deleteWord.setOnAction(event -> {
                confirmationDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            wordField.setText("");
                            pronounceField.setText("");
                            explainField.getChildren().clear();

                            speech.setDisable(true);
                            deleteWord.setDisable(true);
                            speech.setVisible(false);
                            deleteWord.setVisible(false);

                            Dictionary.getInstance().removeWord(word);
                            Notifications.create()
                                    .owner(rootPane)
                                    .text("Word has been delete")
                                    .showInformation();

                            updateSuggestions();
                            return;
                        } catch (final SQLException e) {
                            Notifications.create()
                                    .owner(rootPane)
                                    .text("Error when delete word")
                                    .showInformation();
                        }
                    }
                });
            });

            wordField.setText(word.getWord());
            pronounceField.setText('/' + word.getPronunce() + '/');

            final ArrayList<Explain> explains = database.getExplainsByWordID(word.getWordID());
            for (final Explain explain : explains) {
                final Label type = new Label(explain.getType() + "\n");
                final Label meaning = new Label("\t" + explain.getMeaning() + "\n");
                type.getStyleClass().add("level1");
                type.setWrapText(true);
                meaning.getStyleClass().add("level2");
                meaning.setWrapText(true);
                explainField.getChildren().add(type);
                explainField.getChildren().add(meaning);

                final ArrayList<Example> examples = database.getExamples(explain.getExplainID());
                for (final Example example : examples) {
                    final Label exampleText = new Label("\t\t" + example.getExample() + "\n");
                    exampleText.getStyleClass().add("level3");
                    exampleText.setWrapText(true);
                    explainField.getChildren().add(exampleText);
                }
            }
        } catch (final SQLException e) {
            wordField.setText(null);
            explainField.getChildren().clear();
            final Label error = new Label("Đã có lỗi xảy ra");
            explainField.getChildren().add(error);
        }
    }

    @FXML
    private void onPlayAudioButton() {
        final Thread thread = new Thread(() -> TextToSpeechOfflineAPI.getTextToSpeech(wordField.getText()));
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void onSearchBarInputTextChanged() {
        updateSuggestions();
    }

    @FXML
    private void onSearchBarKeyPressed(final KeyEvent event) {
        stopSpeechToTextProcess();
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
                final ArrayList<Pair<String, String>> wordPairs = Dictionary.getInstance().lookup(searchString);
                lazyLoadManager.updateSuggestionsFromNonGUIThread(wordPairs);
            } catch (final Exception e) {
                Platform.runLater(() -> handleException(e));
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
        final Pair<String, String> p = suggestedWords.getSelectionModel().getSelectedItem();
        if (p == null) {
            return;
        }

        try {
            showDetail(p.getSecond());
        } catch (final Exception e) {
            handleException(e);
        }

        searchBar.setText(p.getFirst());
        focusOnSearchBar();
    }

    /**
     * Shows details of the best-match word, i.e. the word that is on top
     * of search results (suggestions).
     */
    private void pickBestMatch() {
        if (suggestedWords.getItems().isEmpty()) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
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

    private static void handleException(final Exception e) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Lỗi: " + e.getMessage());
        alert.setContentText("Vui lòng thử lại sau");
        alert.showAndWait();
    }

    private void stopSpeechToTextProcess() {
        if (speechToTextThread != null && speechToTextThread.isAlive()) {
            isProcessingSpeechToText = false;
            speechToTextThread.interrupt();
            updateSpeechToTextButton();
        }
    }

    private void updateSpeechToTextButton() {
        Platform.runLater(() -> {
            if (isProcessingSpeechToText) {
                final ProgressIndicator spinner = new ProgressIndicator();
                speechToTextButton.setGraphic(spinner);
                speechToTextButton.getStyleClass().remove("accent");

                speechToTextButton.setOnAction(event -> {
                    isProcessingSpeechToText = false;
                    stopSpeechToTextProcess();
                });
            } else {
                speechToTextIcon = new FontAwesomeIconView(FontAwesomeIcon.MICROPHONE);
                speechToTextIcon.setSize("20px");
                speechToTextButton.setOnAction(event -> speechToText());
                speechToTextIcon.getStyleClass().add("ikonli-font-icon");
                speechToTextButton.getStyleClass().add("accent");
                speechToTextButton.setGraphic(speechToTextIcon);
            }
        });
    }

    @FXML
    private void speechToText() {
        if (!RecordManager.isRecording()) {
            Notifications.create()
                    .owner(rootPane)
                    .text(bundle.getString("start_record"))
                    .showInformation();

            speechToTextButton.getStyleClass().add("danger");
            recordThread = new Thread(() -> {
                RecordManager.startRecording(speechFile);
            });

            recordThread.setDaemon(true);
            recordThread.start();
            return;
        }

        RecordManager.stopRecording();
        speechToTextButton.getStyleClass().remove("danger");

        Notifications.create()
                .owner(rootPane)
                .text(bundle.getString("stop_record"))
                .showInformation();

        isProcessingSpeechToText = true;
        updateSpeechToTextButton();

        speechToTextThread = new Thread(() -> {
            try {
                String searchResult;
                if (Utils.isNetworkConnected()) {
                    searchResult = SpeechToTextOnlineAPI.getSpeechToText(speechFile);
                } else {
                    searchResult = SpeechToTextOfflineAPI.getSpeechToText(speechFile);
                }

                Platform.runLater(() -> searchBar.setText(searchResult));
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                isProcessingSpeechToText = false;
                updateSpeechToTextButton();
            }
        });

        speechToTextThread.setDaemon(true);
        speechToTextThread.start();
    }
}
