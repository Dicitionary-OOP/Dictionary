package dictionary.base;

public enum Language {
    VIETNAMESE("vi"),
    ENGLISH("en");

    public String code;

    private Language(String code) {
        this.code = code;
    }
}
