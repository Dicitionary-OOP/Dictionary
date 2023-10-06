package dictionary.graphic.controllers;

import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {
    @FXML
    public void onLookupTabClick() throws IOException {
        SceneController.switchToLookup();
    }

    @FXML
    public void onTranslateTabClick() throws IOException {
        SceneController.switchToTranslate();
    }
}
