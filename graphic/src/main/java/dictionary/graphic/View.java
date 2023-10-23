package dictionary.graphic;

public enum View {
    EnglishVietnamese("/views/english-vietnamese.fxml"),
    VietnameseEnglish("/views/vietnamese-english.fxml"),
    Translate("/views/translate.fxml"),
    TextToIpa("/views/text-to-ipa.fxml"),
    Synonym("/views/synonym.fxml"),
    Settings("/views/settings.fxml");

    private final String fxml;

    private View(final String fxml) {
        this.fxml = fxml;
    }

    public String fxml(){
        return this.fxml;
    }
}

