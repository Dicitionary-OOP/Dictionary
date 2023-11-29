package dictionary.graphic;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import dictionary.base.utils.Utils;
import dictionary.graphic.controllers.SceneController;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application
{
    @Override public void start(final Stage stage) throws IOException
    {
        stage.setTitle("Dictionary");
        stage.setResizable(false);
        stage.getIcons().add(
            new Image(new FileInputStream((Utils.getResource("/icons/logo.png")))));

        SceneController.getInstance().init(stage);
        SceneController.getInstance().switchScene(View.Main.fxml());
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    }

    public static void main(final String[] args) { launch(args); }
}
