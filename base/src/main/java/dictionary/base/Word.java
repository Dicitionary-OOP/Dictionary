package dictionary.base;

public class Word {
    private String wordTargett;
    private WordExplain wordExplain;

    public Word(final String wordTarget, final WordExplain wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(final String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public WordExplain getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(final WordExplain wordExplain) {
        this.wordExplain = wordExplain;
    }
}
