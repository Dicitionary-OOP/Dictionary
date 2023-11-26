package dictionary.graphic.controllers;

import dictionary.base.utils.Utils;
import dictionary.graphic.Font;
import dictionary.graphic.Theme;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SceneController
{
    private static SceneController INSTANCE;

    private Stage stage;
    private Scene scene;
    private FXMLLoader root;
    private Parent parent;

    private Locale locale;
    private ResourceBundle bundle;
    private Theme theme;
    private Font font;

    private SceneController()
    {
        theme = Theme.PrimerLight;
        font = Font.Inter;
    }

    public static SceneController getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new SceneController();
        }

        return INSTANCE;
    }

    public Stage getStage() { return stage; }

    public Scene getScene() { return scene; }

    public FXMLLoader getRoot() { return root; }

    public void init(Stage _stage) throws IOException
    {
        this.stage = _stage;
        this.locale = new Locale("en");
        this.bundle = ResourceBundle.getBundle("languages.language", locale);
        this.scene = new Scene(new BorderPane());
        setTheme(theme);
    }

    public void switchScene(String scene)
    {
        try {
            this.root = new FXMLLoader(SceneController.class.getResource(scene), bundle);
            this.parent = root.load();
            renderScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderScene()
    {
        this.scene.setRoot(parent);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public Locale getLocale() { return locale; }

    public Theme getTheme() { return theme; }

    public void setTheme(Theme theme)
    {
        this.theme = theme;
        applyCss();
    }

    public Font getFont() { return font; }

    public void setFont(Font font)
    {
        this.font = font;
        applyCss();
    }

    private void applyCss()
    {
        this.scene.getStylesheets().clear();
        this.scene.getStylesheets().add(theme.style());
        this.scene.getStylesheets().add(
            Objects.requireNonNull(SceneController.class.getResource("/css/global.css"))
                .toExternalForm());
        this.scene.getStylesheets().add(
            Objects.requireNonNull(SceneController.class.getResource("/css/wordle.css"))
                .toExternalForm());
        this.scene.getStylesheets().add(font.path());
    }

    public void setLocale(String localeStr)
    {
        this.locale = new Locale(localeStr);
        this.bundle = ResourceBundle.getBundle("languages.language", this.locale);
    }
}
