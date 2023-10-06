package dictionary.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    private Scene scene;
    private Parent parent;

    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader root = new FXMLLoader(getClass().getResource("/views/translate-view.fxml"));
        parent = root.load();
        scene = new Scene(parent, 800, 450, false, SceneAntialiasing.BALANCED);
        scene.setRoot(parent);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
