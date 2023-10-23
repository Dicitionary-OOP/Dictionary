package dictionary.graphic.controllers;

import javafx.fxml.FXML;

import dictionary.graphic.View;

public class NavbarController {
    @FXML
    private void initialize() {
    }

    @FXML
    private void switchToEnglishVietnamese() {
        SceneController.getInstance().switchScene(View.EnglishVietnamese.fxml());
    }

    @FXML
    private void switchToVietnameseEnglish() {
        SceneController.getInstance().switchScene(View.VietnameseEnglish.fxml());
    }

    @FXML
    private void switchToTranslate() {
        SceneController.getInstance().switchScene(View.Translate.fxml());
    }

    @FXML
    private void switchToTextToIPA() {
        SceneController.getInstance().switchScene(View.TextToIpa.fxml());
    }

    @FXML
    private void switchToSynonym() {
        SceneController.getInstance().switchScene(View.Synonym.fxml());
    }

    @FXML
    private void switchToSettings() {
        SceneController.getInstance().switchScene(View.Settings.fxml());
    }
}
