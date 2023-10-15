package dictionary.base;

public class Word {
    private String target;
    private WordExplain explain;
    private Language language;

    public Word(final String wordTarget, final WordExplain wordExplain, final Language language) {
        this.target = wordTarget;
        this.explain = wordExplain;
        this.language = language;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String wordTarget) {
        this.target = wordTarget;
    }

    public WordExplain getExplain() {
        return explain;
    }

    public void setExplain(final WordExplain wordExplain) {
        this.explain = wordExplain;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
