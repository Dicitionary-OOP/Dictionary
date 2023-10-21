package dictionary.graphic.controllers;

import javafx.fxml.FXML;

public class NavbarController {
    @FXML
    private void initialize() {
    }

    @FXML
    private void switchToEnglishVietnamese() {
        SceneController.getInstance().switchScene("/views/english-vietnamese.fxml");
    }

    @FXML
    private void switchToVietnameseEnglish() {
        SceneController.getInstance().switchScene("/views/vietnamese-english.fxml");
    }

    @FXML
    private void switchToTranslate() {
        SceneController.getInstance().switchScene("/views/translate.fxml");
    }

    @FXML
    private void switchToTextToIPA() {
        SceneController.getInstance().switchScene("/views/text-to-ipa.fxml");
    }

    @FXML
    private void switchToSynonym() {
        SceneController.getInstance().switchScene("/views/synonym.fxml");
    }

    @FXML
    private void switchToSettings() {
        SceneController.getInstance().switchScene("/views/settings.fxml");
    }
}
