package dictionary.graphic.controllers;

import java.util.Objects;

import org.kordamp.bootstrapfx.BootstrapFX;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScoreWindow {

    public static BooleanProperty resetGame = new SimpleBooleanProperty(false);

    private ScoreWindow() {
    }

    public static void display(final boolean guessed, final String winningWord) {
        final Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setMaxWidth(500);
        stage.setMaxHeight(460);

        final VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);

        final Label mainLabel = new Label();
        if (guessed) {
            mainLabel.setText("           You won! \n The winning word was");
            mainLabel.getStyleClass().setAll("lead", "big-font");
        } else {
            mainLabel.setText("           You lost! \n The winning word was");
            mainLabel.getStyleClass().setAll("big-font");
        }
        final Label winningWordLabel = new Label(winningWord.toUpperCase());
        winningWordLabel.getStyleClass().setAll("h2", "strong");

        final VBox buttonsVBox = new VBox(5);
        buttonsVBox.setAlignment(Pos.CENTER);

        final Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.getStyleClass().setAll("btn", "btn-primary");
        playAgainButton.setOnMouseClicked(me -> {
            resetGame.set(true);
            stage.close();
        });

        buttonsVBox.getChildren().addAll(playAgainButton);

        root.getChildren().addAll(mainLabel, winningWordLabel, buttonsVBox);
        final Scene scene = new Scene(root, 300, 260);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets()
                .add(Objects.requireNonNull(ScoreWindow.class.getResource("/css/wordle.css"))
                        .toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }
}
