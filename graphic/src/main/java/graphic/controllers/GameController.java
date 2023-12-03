package graphic.controllers;

import java.util.ArrayList;
import java.util.Objects;

import base.core.Dictionary;
import graphic.windows.HelpWindow;
import graphic.windows.ScoreWindow;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

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
    private void onKeyPressed(final KeyEvent keyEvent) {
        onKeyPressed(gridPane, keyEvent);
    }

    public void showHelp() {
        HelpWindow.display();
    }

    @FXML
    private void onRestart() {
        resetGame(gridPane);
    }

    public void createGrid(final GridPane gridPane) {
        for (int i = 1; i <= MAX_ROW; i++) {
            for (int j = 1; j <= MAX_COLUMN; j++) {
                final Label label = new Label();
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    private void setLabelText(final GridPane gridPane, final int searchRow, final int searchColumn,
            final String input) {
        final Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.setText(input.toUpperCase());
        }
    }

    private Label getLabel(final GridPane gridPane, final int searchRow, final int searchColumn) {
        for (final Node child : gridPane.getChildren()) {
            final Integer r = GridPane.getRowIndex(child);
            final Integer c = GridPane.getColumnIndex(child);
            final int row = r == null ? 0 : r;
            final int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label)) {
                return (Label) child;
            }
        }
        return null;
    }

    private String getLabelText(final GridPane gridPane, final int searchRow, final int searchColumn) {
        final Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            return label.getText().toLowerCase();
        }
        return null;
    }

    private void setLabelStyleClass(final GridPane gridPane, final int searchRow, final int searchColumn,
            final String styleclass) {
        final Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    private void clearLabelStyleClass(final GridPane gridPane, final int searchRow, final int searchColumn) {
        final Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    private void updateRowColors(final GridPane gridPane, final int searchRow) {
        for (int i = 1; i <= MAX_COLUMN; i++) {
            final Label label = getLabel(gridPane, searchRow, i);
            String styleClass;
            if (label != null) {
                final String currentCharacter = String.valueOf(label.getText().charAt(0)).toLowerCase();
                if (String.valueOf(winningWord.charAt(i - 1))
                        .toLowerCase()
                        .equals(currentCharacter)) {
                    styleClass = "correct-letter";
                } else if (winningWord.contains(currentCharacter)) {
                    styleClass = "present-letter";
                } else {
                    styleClass = "wrong-letter";
                }

                final FadeTransition firstFadeTransition = new FadeTransition(Duration.millis(300), label);
                firstFadeTransition.setFromValue(1);
                firstFadeTransition.setToValue(0.2);
                firstFadeTransition.setOnFinished(e -> {
                    label.getStyleClass().clear();
                    label.getStyleClass().setAll(styleClass);
                });

                final FadeTransition secondFadeTransition = new FadeTransition(Duration.millis(300), label);
                secondFadeTransition.setFromValue(0.2);
                secondFadeTransition.setToValue(1);

                new SequentialTransition(firstFadeTransition, secondFadeTransition).play();
            }
        }
    }

    private String getWordFromCurrentRow(final GridPane gridPane) {
        final StringBuilder input = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++) {
            input.append(getLabelText(gridPane, CURRENT_ROW, j));
        }
        return input.toString();
    }

    public void onKeyPressed(
            final GridPane gridPane,
            final KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspacePressed(gridPane);
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterPressed(gridPane, keyEvent);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterPressed(gridPane);
        }
    }

    private void onBackspacePressed(final GridPane gridPane) {
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

    private void onLetterPressed(final GridPane gridPane, final KeyEvent keyEvent) {
        if (Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, keyEvent.getText());
            final Label label = getLabel(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            final ScaleTransition firstScaleTransition = new ScaleTransition(Duration.millis(100), label);
            firstScaleTransition.fromXProperty().setValue(1);
            firstScaleTransition.toXProperty().setValue(1.1);
            firstScaleTransition.fromYProperty().setValue(1);
            firstScaleTransition.toYProperty().setValue(1.1);
            final ScaleTransition secondScaleTransition = new ScaleTransition(Duration.millis(100), label);
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

    private void onEnterPressed(final GridPane gridPane) {
        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
            final String guess = getWordFromCurrentRow(gridPane).toLowerCase();
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
        try {
            winningWord = Dictionary.getInstance().getRandomWordByLength(MAX_COLUMN);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidGuess(final String guess) {
        return guess.length() == MAX_COLUMN && Dictionary.getInstance().isExistWord(guess);
    }

    public void resetGame(final GridPane gridPane) {
        getRandomWord();
        Label label;
        for (final Node child : gridPane.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.setText("");
                label.getStyleClass().add("default-tile");
            }

        CURRENT_COLUMN = 1;
        CURRENT_ROW = 1;
    }
}
