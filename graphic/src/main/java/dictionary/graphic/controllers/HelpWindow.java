package dictionary.graphic.controllers;

import java.util.ArrayList;
import java.util.Objects;

import org.kordamp.bootstrapfx.BootstrapFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelpWindow {

    public static void display() {
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("HOW TO PLAY");

        final VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));
        final Label helpParagraph = new Label(
                "Guess the WORDLE in six tries. \n Each guess must be a valid five-letter word.\n Hit the enter button to submit.\n\n After each guess, the color of the tiles will change to \n show how close your guess was to the word.");
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().setAll("lead");

        final Line line1 = new Line();
        line1.setStroke(Paint.valueOf("b8b8b8"));
        line1.setEndX(2000);

        final Label labelExample = new Label("Examples");
        labelExample.getStyleClass().setAll("h3");
        labelExample.setTextAlignment(TextAlignment.LEFT);

        /* FIRST WORD */

        final ArrayList<Label> firstWord = new ArrayList<>();
        final Label label1 = new Label("W");
        label1.getStyleClass().setAll("correct-letter-example");
        firstWord.add(label1);
        for (final String letter : new String[] { "E", "A", "R", "Y" }) {
            final Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            firstWord.add(label);
        }
        final HBox firstWordVBox = new HBox(3);
        for (final Label label : firstWord)
            firstWordVBox.getChildren().add(label);
        final Label firstWordLabel = new Label("The letter W is in the word and in the correct spot.");
        firstWordLabel.getStyleClass().setAll("lead");

        /* SECOND WORD */

        final ArrayList<Label> secondWord = new ArrayList<>();
        final Label labelP = new Label("P");
        labelP.getStyleClass().setAll("default-letter-example");
        final Label labelI = new Label("I");
        labelI.getStyleClass().setAll("present-letter-example");
        secondWord.add(labelP);
        secondWord.add(labelI);
        for (final String letter : new String[] { "L", "L", "S" }) {
            final Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            secondWord.add(label);
        }
        final HBox secondWordVBox = new HBox(3);
        for (final Label label : secondWord)
            secondWordVBox.getChildren().add(label);
        final Label secondWordLabel = new Label("The letter I is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().setAll("lead");

        /* THIRD WORD */

        final ArrayList<Label> thirdWord = new ArrayList<>();
        for (final String letter : new String[] { "V", "A", "G" }) {
            final Label label = new Label(letter);
            label.getStyleClass().setAll("default-letter-example");
            thirdWord.add(label);
        }
        final Label labelU = new Label("U");
        labelU.getStyleClass().setAll("wrong-letter-example");
        final Label labelE = new Label("E");
        labelE.getStyleClass().setAll("default-letter-example");
        thirdWord.add(labelU);
        thirdWord.add(labelE);
        final HBox thirdWordVBox = new HBox(3);
        for (final Label label : thirdWord)
            thirdWordVBox.getChildren().add(label);
        final Label thirdWordLabel = new Label("The letter U is not in the word in any spot.");
        thirdWordLabel.getStyleClass().setAll("lead");

        final Line line2 = new Line();
        line2.setStroke(Paint.valueOf("b8b8b8"));
        line2.setEndX(2000);

        final Button goBackButton = new Button("GO BACK");
        goBackButton.getStyleClass().setAll("btn", "btn-primary");

        goBackButton.setOnMouseClicked(me -> stage.close());

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(helpParagraph, line1, labelExample, firstWordVBox,
                firstWordLabel, secondWordVBox, secondWordLabel, thirdWordVBox, thirdWordLabel,
                line2, goBackButton);
        final Scene scene = new Scene(root, 500, 515);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets()
                .add(Objects.requireNonNull(ScoreWindow.class.getResource("/css/wordle.css"))
                        .toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

}
