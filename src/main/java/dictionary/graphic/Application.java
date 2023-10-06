package dictionary.graphic;

import java.io.IOException;

import dictionary.graphic.controllers.SceneController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(final Stage stage) throws IOException {
        SceneController.initializeApplication(stage,
                new FXMLLoader(getClass().getResource("/views/translate-view.fxml")));
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
