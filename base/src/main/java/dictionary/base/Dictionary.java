package dictionary.base;
import dictionary.base.algorithm.trie.Trie;

import java.util.ArrayList;

public class Dictionary {
    private final Trie<Word> words;

    public Dictionary() {
        words = new Trie<>();
    }

    public void add(final Word word) {
        words.add(word.getWordTarget(), word);
    }

    public ArrayList<Word> getWords() {
        return lookup("");
    }

    /**
     * Use linear search to find lookupWord in words
     *
     * @param lookupWord word to lookup
     * @return List of words matched lookupWord
     */
    public ArrayList<Word> lookup(final String lookupWord) {
        return words.findWithPrefix(lookupWord);
    }

    /**
     * Remove a word by word
     *
     * @param word to remove
     */
    public void removeWord(final Word word) {
        words.remove(word.getWordTarget());
    }
}
