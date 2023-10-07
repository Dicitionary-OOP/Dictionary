package dictionary.cli;

public class Word {
    private String wordTarget;
    private WordExplain wordExplain;

    Word(final String wordTarget, final WordExplain wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    String getWordTarget() {
        return wordTarget;
    }

    void setWordTarget(final String wordTarget) {
        this.wordTarget = wordTarget;
    }

    WordExplain getWordExplain() {
        return wordExplain;
    }

    void setWordExplain(final WordExplain wordExplain) {
        this.wordExplain = wordExplain;
    }
}
