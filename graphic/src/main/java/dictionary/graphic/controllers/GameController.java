package dictionary.graphic.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import dictionary.base.Dictionary;

public class GameController {
    @FXML
    public GridPane gridPane;

    public static final ArrayList<String> dictionaryWords = new ArrayList<>();
    private String winningWord;

    private int CURRENT_ROW = 1;
    private int CURRENT_COLUMN = 1;
    private final int MAX_COLUMN = 5;
    private final int MAX_ROW = 6;

    @FXML
    private void initialize() {
        createUI();
        getRandomWord();
    }

    public void createUI() {
        createGrid();
    }

    public void createGrid() {
        createGrid(gridPane);
    }

    @FXML
    private void gridPaneRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        onKeyPressed(gridPane, keyEvent);
    }

    public void showHelp() {
        HelpWindow.display();
    }

    @FXML
    private void onRestart() {
        resetGame(gridPane);
    }

    public void createGrid(GridPane gridPane) {
        for (int i = 1; i <= MAX_ROW; i++) {
            for (int j = 1; j <= MAX_COLUMN; j++) {
                Label label = new Label();
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    private void setLabelText(GridPane gridPane, int searchRow, int searchColumn, String input) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.setText(input.toUpperCase());
        }
    }

    private Label getLabel(GridPane gridPane, int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = r == null ? 0 : r;
            int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label)) {
                return (Label) child; 
            }
        }
        return null;
    }

    private Label getLabel(GridPane gridPane, String letter) {
        Label label;
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label) {
                label = (Label) child;
                if (letter.equalsIgnoreCase(label.getText())) {
                    return label; 
                }
            }
        }
        return null;
    }

    private String getLabelText(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) { 
            return label.getText().toLowerCase();
        }
        return null;
    }

    private void setLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn, String styleclass) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    private void clearLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    private void updateRowColors(GridPane gridPane, int searchRow) {
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            String styleClass;
            if (label != null) {
                String currentCharacter = String.valueOf(label.getText().charAt(0)).toLowerCase();
                if (String.valueOf(winningWord.charAt(i - 1))
                        .toLowerCase()
                        .equals(currentCharacter)) {
                    styleClass = "correct-letter";
                } else if (winningWord.contains(currentCharacter)) {
                    styleClass = "present-letter";
                } else {
                    styleClass = "wrong-letter";
                }

                FadeTransition firstFadeTransition = new FadeTransition(Duration.millis(300), label);
                firstFadeTransition.setFromValue(1);
                firstFadeTransition.setToValue(0.2);
                firstFadeTransition.setOnFinished(e -> {
                    label.getStyleClass().clear();
                    label.getStyleClass().setAll(styleClass);
                });

                FadeTransition secondFadeTransition = new FadeTransition(Duration.millis(300), label);
                secondFadeTransition.setFromValue(0.2);
                secondFadeTransition.setToValue(1);

                new SequentialTransition(firstFadeTransition, secondFadeTransition).play();
            }
        }
    }

    private String getWordFromCurrentRow(GridPane gridPane) {
        StringBuilder input = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++) {
            input.append(getLabelText(gridPane, CURRENT_ROW, j)); 
        }
        return input.toString();
    }

    public void onKeyPressed(
            GridPane gridPane,
            KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspacePressed(gridPane);
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterPressed(gridPane, keyEvent);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterPressed(gridPane);
        }
            }

    private void onBackspacePressed(GridPane gridPane) {
        if ((CURRENT_COLUMN == MAX_COLUMN || CURRENT_COLUMN == 1)
                && !Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, "");
            clearLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "default-tile");
        } else if ((CURRENT_COLUMN > 1 && CURRENT_COLUMN < MAX_COLUMN)
                || (CURRENT_COLUMN == MAX_COLUMN
                    && Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), ""))) {
            CURRENT_COLUMN--;
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, "");
            clearLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "default-tile");
                    }
    }

    private void onLetterPressed(GridPane gridPane, KeyEvent keyEvent) {
        if (Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, keyEvent.getText());
            Label label = getLabel(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            ScaleTransition firstScaleTransition = new ScaleTransition(Duration.millis(100), label);
            firstScaleTransition.fromXProperty().setValue(1);
            firstScaleTransition.toXProperty().setValue(1.1);
            firstScaleTransition.fromYProperty().setValue(1);
            firstScaleTransition.toYProperty().setValue(1.1);
            ScaleTransition secondScaleTransition = new ScaleTransition(Duration.millis(100), label);
            secondScaleTransition.fromXProperty().setValue(1.1);
            secondScaleTransition.toXProperty().setValue(1);
            secondScaleTransition.fromYProperty().setValue(1.1);
            secondScaleTransition.toYProperty().setValue(1);
            new SequentialTransition(firstScaleTransition, secondScaleTransition).play();

            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "tile-with-letter");
            if (CURRENT_COLUMN < MAX_COLUMN) {
                CURRENT_COLUMN++; 
            }
        }
    }

    private void onEnterPressed(GridPane gridPane) {
        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winningWord)) {
                updateRowColors(gridPane, CURRENT_ROW);
                ScoreWindow.display(true, winningWord);
            } else if (isValidGuess(guess)) {
                updateRowColors(gridPane, CURRENT_ROW);
                if (CURRENT_ROW == MAX_ROW) {
                    ScoreWindow.display(false, winningWord);
                    if (ScoreWindow.resetGame.get()) {
                        resetGame(gridPane); 
                    }
                }
                CURRENT_ROW++;
                CURRENT_COLUMN = 1;
            } 

            if (ScoreWindow.resetGame.get()) {
                resetGame(gridPane);
                ScoreWindow.resetGame.set(false);
            }
        }
    }

    public void getRandomWord() {
        winningWord = Dictionary.getInstance().getRandomWordByLength(MAX_COLUMN);
    }

    private boolean isValidGuess(String guess) {
        return Dictionary.getInstance().isExistWord(guess);
    }

    public void resetGame(GridPane gridPane) {
        getRandomWord();
        Label label;
        for (Node child : gridPane.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.setText("");
                label.getStyleClass().add("default-tile");
            }

        CURRENT_COLUMN = 1;
        CURRENT_ROW = 1;
    }

    private boolean contains(String[] array, String letter) {
        for (String string : array) {
            if (string.equalsIgnoreCase(letter)) {
                return true; 
            }
        }
        return false;
    }
}

