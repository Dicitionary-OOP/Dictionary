package graphic.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;

import org.apache.commons.math3.util.Pair;
import org.controlsfx.control.Notifications;

import base.api.SpeechToTextOfflineAPI;
import base.api.SpeechToTextOnlineAPI;
import base.api.TextToSpeechOfflineAPI;
import base.core.Dictionary;
import base.core.Example;
import base.core.Explain;
import base.core.Word;
import base.database.Database;
import base.utils.RecordManager;
import base.utils.Utils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import graphic.managers.LazyLoadManager;
import graphic.windows.EditWordWindow;
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

public class EnglishVietnameseController extends LocalizedController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label wordField;

    @FXML
    private Label pronounceField;

    @FXML
    private VBox explainField;

    @FXML
    private TextField searchInput;

    @FXML
    private Button speechButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteWordButton;

    @FXML
    private Button speechToTextButton;

    @FXML
    private ListView<Pair<String, String>> suggestedWords;

    private Thread recordThread;

    private Thread speechToTextThread;

    private final ExecutorService searchWordExecutor = Executors.newSingleThreadExecutor();

    private Future<?> searchWordFuture = null;

    private LazyLoadManager lazyLoadManager;

    private FontAwesomeIconView speechToTextIcon;

    private Boolean isProcessingSpeechToText = false;

    private final String speechFile = "speechToText.mp3";

    private Alert deleteConfirm;

    private Alert wordNotFoundAlert;

    private Alert noSpeechAlert;

    @FXML
    private void initialize() {
        lazyLoadManager = new LazyLoadManager(suggestedWords);

        deleteConfirm = new Alert(AlertType.CONFIRMATION);
        deleteConfirm.setTitle("Confirm delete");
        deleteConfirm.setHeaderText("Confirm Action");
        deleteConfirm.setContentText("Are you sure to delete this word?");

        wordNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        wordNotFoundAlert.setTitle("Lỗi");
        wordNotFoundAlert.setContentText("Vui lòng nhập lại từ đúng hoặc thêm từ mới.");

        noSpeechAlert = new Alert(Alert.AlertType.ERROR);
        noSpeechAlert.setTitle("Lỗi");
        noSpeechAlert.setHeaderText("Bạn chưa nói từ nào !");
        noSpeechAlert.setContentText("Vui lòng bấm vào biểu tượng micro và nói lại từ bạn muốn tra, hoặc nhập từ đó bằng bàn phím.");

        setupSuggest();
    }

    private void setupSuggest() {
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
            boolean userScrolledToBottomOfSearchSuggestionList = (event.getDeltaY() < 0);
            if (userScrolledToBottomOfSearchSuggestionList) {
                lazyLoadManager.loadAllSuggestionsFromGUIThread();
            }
        });
        updateSuggestions();
    }

    private void showActionButtons() {
        speechButton.setDisable(false);
        editButton.setDisable(false);
        deleteWordButton.setDisable(false);
        speechButton.setVisible(true);
        editButton.setVisible(true);
        deleteWordButton.setVisible(true);
    }

    private void hideActionButton() {
        speechButton.setDisable(true);
        editButton.setDisable(true);
        deleteWordButton.setDisable(true);
        speechButton.setVisible(false);
        editButton.setVisible(false);
        deleteWordButton.setVisible(false);
    }

    private void resetShowDetail() {
        wordField.setText("");
        pronounceField.setText("");
        explainField.getChildren().clear();

        hideActionButton();
    }

    private void showDetail(final String wordID) {
        try {
            final Database database = Dictionary.getInstance().getDatabase();
            final Word word = database.getWord(wordID);

            explainField.getChildren().clear();
            showActionButtons();

            editButton.setOnAction(event -> {
                new EditWordWindow(word);
                showDetail(wordID);
            });

            deleteWordButton.setOnAction(event -> {
                deleteConfirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            Dictionary.getInstance().removeWord(word);
                            Notifications.create()
                                    .owner(rootPane)
                                    .text("Word has been delete")
                                    .showInformation();

                            resetShowDetail();
                            updateSuggestions();
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

            final ArrayList<Explain> explains = database.getExplains(word.getWordID());
            for (final Explain explain : explains) {
                final Label type = new Label(explain.getType());
                final Label meaning = new Label("\t" + explain.getMeaning());
                type.setWrapText(true);
                meaning.setWrapText(true);
                explainField.getChildren().add(type);
                explainField.getChildren().add(meaning);

                final ArrayList<Example> examples = database.getExamples(explain.getExplainID());
                for (final Example example : examples) {
                    final Label exampleText = new Label("\t\t" + example.getExample());
                    exampleText.setWrapText(true);
                    final Label translate = new Label("\t\t" + example.getTranslate());
                    exampleText.setWrapText(true);
                    translate.setWrapText(true);
                    explainField.getChildren().add(exampleText);
                    explainField.getChildren().add(translate);
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
        final Thread thread = new Thread(() -> TextToSpeechOfflineAPI.speak(wordField.getText()));
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

    private void updateSuggestions() {
        _updateSuggestionsInternal(false);
    }

    private void updateSuggestionsAndPickBestMatch() {
        _updateSuggestionsInternal(true);
    }

    private void _updateSuggestionsInternal(boolean pickBestMatchImmediately) {
        if (searchWordFuture != null && !searchWordFuture.isDone()) {
            searchWordFuture.cancel(true);
        }
        final String searchString = searchInput.getText().trim().toLowerCase();
        searchWordFuture = searchWordExecutor.submit(() -> {
            try {
                final ArrayList<Pair<String, String>> wordPairs = Dictionary.getInstance().lookup(searchString);
                if (!pickBestMatchImmediately) {
                    lazyLoadManager.updateSuggestionsFromNonGUIThread(wordPairs);
                } else {
                    Platform.runLater(() -> {
                        lazyLoadManager.updateSuggestionsFromGUIThread(wordPairs);
                        pickBestMatch();
                    });
                }
            } catch (final Exception e) {
                Platform.runLater(() -> handleException(e));
            }
        });
    }

    private void useSuggestions() {
        if (suggestedWords.getItems().isEmpty()) {
            return;
        }
        suggestedWords.requestFocus();
        suggestedWords.getSelectionModel().select(0);
    }

    private void pickCurrentSuggestion() {
        final Pair<String, String> selectedItem = suggestedWords.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        try {
            showDetail(selectedItem.getSecond());
        } catch (final Exception e) {
            handleException(e);
        }

        focusOnSearchBar();
    }

    private void pickBestMatch() {
        if (suggestedWords.getItems().isEmpty()) {
            wordNotFoundAlert.setHeaderText(String.format("Không tìm thấy từ tương tự \'%s\'", searchInput.getText()));
            wordNotFoundAlert.showAndWait();
            return;
        }

        suggestedWords.getSelectionModel().select(0);
        pickCurrentSuggestion();
    }

    private void focusOnSearchBar() {
        searchInput.requestFocus();
        searchInput.deselect();
        searchInput.positionCaret(searchInput.getText().length());
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
                    searchResult = SpeechToTextOnlineAPI.getText(speechFile);
                } else {
                    searchResult = SpeechToTextOfflineAPI.getText(speechFile);
                }

                Platform.runLater(() -> {
                    if (searchResult.isEmpty()) {
                        noSpeechAlert.showAndWait();
                        return;
                    }
                    searchInput.setText(searchResult);
                    // Immediately show detail of the spoken word !
                    updateSuggestionsAndPickBestMatch();
                });
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
