package dictionary;

public class Word {
    private String wordTarget;
    private String wordExplain;

    Word(final String wordTarget, final String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    String getWordTarget() {
        return wordTarget;
    }

    void setWordTarget(final String wordTarget) {
        this.wordTarget = wordTarget;
    }

    String getWordExplain() {
        return wordExplain;
    }

    void setWordExplain(final String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
