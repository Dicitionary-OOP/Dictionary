package dictionary.graphic;

import java.io.IOException;

import dictionary.graphic.controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        SceneController.getInstance().init(stage);
        SceneController.getInstance().switchScene("/views/english-vietnamese.fxml");
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
