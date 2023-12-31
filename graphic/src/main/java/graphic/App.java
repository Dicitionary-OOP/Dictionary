package graphic;

import java.io.FileInputStream;
import java.io.IOException;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import base.core.Dictionary;
import base.utils.Utils;
import graphic.controllers.SceneController;
import graphic.enums.View;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(final Stage stage) throws IOException {
        stage.setTitle("Dictionary");
        stage.getIcons().add(
                new Image(new FileInputStream((Utils.getResource("/icons/logo.png")))));
        stage.setResizable(false);

        SceneController.getInstance().init(stage);
        SceneController.getInstance().switchScene(View.Main.fxml());
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
    }

    @Override
    public void stop(){
        try {
            Dictionary.getInstance().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(final String[] args) {
        launch(args);
    }
}
