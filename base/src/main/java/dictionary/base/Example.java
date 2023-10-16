package dictionary.base;

public class Example {
    private String example;
    private String translate;

    public Example(String example, String translate) {
        this.example = example;
        this.translate = translate;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
