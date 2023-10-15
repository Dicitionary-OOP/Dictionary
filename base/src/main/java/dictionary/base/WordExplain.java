package dictionary.base;

public class WordExplain {
    private String type;
    private String pronounce;
    private String meaning;
    private String examples;
    private String language;

    public WordExplain(String type, String pronounce, String meaning, String examples, String language) {
        this.type = type;
        this.pronounce = pronounce;
        this.meaning = meaning;
        this.examples = examples;
        this.language = language;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronunce) {
        this.pronounce = pronunce;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
