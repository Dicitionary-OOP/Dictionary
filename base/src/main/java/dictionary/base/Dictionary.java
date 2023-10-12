package dictionary.base;

import java.util.ArrayList;

public class Dictionary {
    private final ArrayList<Word> words;

    public Dictionary() {
        words = new ArrayList<>();
    }

    public void add(final Word word) {
        words.add(word);
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    /**
     * Use linear search to find lookupWord in words
     *
     * @param lookupWord word to lookup
     * @return List of words matched lookupWord
     */
    public ArrayList<Word> lookup(final String lookupWord) {
        final ArrayList<Word> result = new ArrayList<>();
        for (final Word word : words) {
            if (word.getWordTarget().equals(lookupWord)) {
                result.add(word);
            }
        }
        return result;
    }

    /**
     * Remove a word by index
     *
     * @param index word's index
     */
    public void removeWord(final int index) {
        if (0 <= index && index < words.size()) {
            words.remove(index);
        }
    }
}
