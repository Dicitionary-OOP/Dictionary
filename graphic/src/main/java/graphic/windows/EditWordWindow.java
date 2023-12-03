package graphic.windows;

import java.util.Locale;
import java.util.ResourceBundle;

import base.core.Word;
import graphic.controllers.EditWordController;
import graphic.controllers.SceneController;
import graphic.enums.View;
import graphic.managers.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EditWordWindow {
    public EditWordWindow(final Word word) {
        try {
            final Stage stage = new Stage();
            final ResourceBundle bundle = ResourceBundle.getBundle("languages.language",
                    new Locale(SettingsManager.getInstance().getProperty("language")));
            final FXMLLoader root = new FXMLLoader(getClass().getResource(View.EditWord.fxml()), bundle);
            final EditWordController controller = new EditWordController(stage);
            root.setController(controller);

            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(SceneController.getInstance().getStage());
            stage.setResizable(false);

            final Scene scene = new Scene(root.load());
            controller.loadWord(word);

            stage.setScene(scene);
            stage.show();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
