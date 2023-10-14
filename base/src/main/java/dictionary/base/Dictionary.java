package dictionary.base;

import dictionary.base.algorithm.trie.Trie;
import java.util.ArrayList;

/**
 * A dictionary that uses a Trie data structure to manage and look up words.
 */
public class Dictionary {
    private final Trie<Word> words;

    /**
     * Initializes a new Dictionary with an empty Trie.
     */
    public Dictionary() {
        words = new Trie<>();
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word The Word object to add.
     */
    public void add(final Word word) {
        words.add(word.getWordTarget(), word);
    }

    /**
     * Retrieves a list of all words in the dictionary.
     *
     * @return An ArrayList of all words in the dictionary.
     */
    public ArrayList<Word> getAllWords() {
        return lookup("");
    }

    /**
     * Looks up words in the dictionary with a given prefix.
     *
     * @param lookupWord The prefix to search for.
     * @return An ArrayList of words that match the given prefix.
     */
    public ArrayList<Word> lookup(final String lookupWord) {
        return words.findWithPrefix(lookupWord);
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param word The Word object to remove.
     */
    public void removeWord(final Word word) {
        words.remove(word.getWordTarget());
    }
}
