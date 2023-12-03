package graphic.enums;

public enum View {
    Main("/views/main.fxml"),
    EnglishVietnamese("/views/english-vietnamese.fxml"),
    Translate("/views/translate.fxml"),
    TextToIpa("/views/text-to-ipa.fxml"),
    Synonym("/views/synonym.fxml"),
    AddWord("/views/add-word.fxml"),
    Game("/views/game.fxml"),
    Settings("/views/settings.fxml");

    private final String fxml;

    private View(final String fxml) {
        this.fxml = fxml;
    }

    public String fxml() {
        return this.fxml;
    }
}
