package dictionary.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dictionary.base.algorithm.trie.Trie;
import dictionary.base.database.DictionaryDatabase;

public class Dictionary {
    private final DictionaryDatabase db;
    private final Trie words;

    public Dictionary() throws IOException, SQLException {
        words = new Trie();
        db = new DictionaryDatabase();
        for (String word : getDatabase().getAllWords()) {
            words.insert(word);
        }
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word The Word object to add.
     */
    public void add(Word word) {
        words.insert(word.getWord());

        try {
            db.addWord(word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of all words in the dictionary.
     *
     * @return An ArrayList of all words in the dictionary.
     */
    public ArrayList<String> getAllWords() {
        return lookup("");
    }

    /**
     * Looks up words in the dictionary with a given prefix.
     *
     * @param lookupWord The prefix to search for.
     * @return An ArrayList of words that match the given prefix.
     */
    public ArrayList<String> lookup(final String lookupWord) {
        return words.getAllWordsStartWith(lookupWord);
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param word The Word object to remove.
     */
    public void removeWord(final String word) {
        words.remove(word);
    }

    public ArrayList<Explain> getWordExplain(String word) throws SQLException {
        return getDatabase().getWordExplain(word);
    }

    public DictionaryDatabase getDatabase() {
        return db;
    }
}
