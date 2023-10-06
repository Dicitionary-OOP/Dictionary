package dictionary.graphic.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static Stage stage;
    private static Scene scene;
    private static FXMLLoader root;
    private static Parent parent;

    public static Stage getStage() {
        return stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static FXMLLoader getRoot() {
        return root;
    }

    public static void initializeApplication(Stage _stage, FXMLLoader _root) throws IOException {
        stage = _stage;
        root = _root;
        parent = root.load();
        scene = new Scene(parent, 800, 450, false, SceneAntialiasing.BALANCED);
        renderScene();
    }

    public static void switchToLookup() throws IOException {
        root = new FXMLLoader(SceneController.class.getResource("/views/lookup-view.fxml"));
        parent = root.load();
        renderScene();
    }

    public static void switchToTranslate() throws IOException {
        root = new FXMLLoader(SceneController.class.getResource("/views/translate-view.fxml"));
        parent = root.load();
        renderScene();
    }

    private static void renderScene() {
        scene.setRoot(parent);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }
}
