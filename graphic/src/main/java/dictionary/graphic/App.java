package dictionary.graphic;

import java.io.FileInputStream;
import java.io.IOException;

import dictionary.base.utils.Utils;
import dictionary.graphic.controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle("Dictionary");
        Image logoApp = new Image(new FileInputStream((Utils.getResource("/dictlogo.png"))));
        stage.getIcons().add(logoApp);
        SceneController.getInstance().init(stage);
        SceneController.getInstance().switchScene(View.Main.fxml());
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
