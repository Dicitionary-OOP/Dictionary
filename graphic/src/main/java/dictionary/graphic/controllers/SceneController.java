package dictionary.graphic.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static SceneController INSTANCE;

    private Locale locale;
    private ResourceBundle bundle;

    private Stage stage;
    private Scene scene;
    private FXMLLoader root;
    private Parent parent;

    private SceneController() {
    }

    public static SceneController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SceneController();
        }

        return INSTANCE;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public FXMLLoader getRoot() {
        return root;
    }

    public void init(Stage _stage) throws IOException {
        this.stage = _stage;
        this.locale = new Locale("en");
        this.bundle = ResourceBundle.getBundle("languages.language", locale);
        this.scene = new Scene(new BorderPane(), 800, 450, false, SceneAntialiasing.BALANCED);
    }

    public void switchScene(String scene) {
        try {
            this.root = new FXMLLoader(SceneController.class.getResource(scene), bundle);
            this.parent = root.load();
            renderScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderScene() {
        this.scene.setRoot(parent);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(String localeStr) {
        this.locale = new Locale(localeStr);
        this.bundle = ResourceBundle.getBundle("languages.language", this.locale);
    }
}
