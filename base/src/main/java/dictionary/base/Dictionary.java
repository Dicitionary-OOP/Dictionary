package dictionary.base;

import dictionary.base.algorithm.trie.Trie;
import dictionary.base.database.DictionaryDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dictionary {
    private final Trie words;
    private final DictionaryDatabase db;

    /**
     * Initializes a new Dictionary with an empty Trie.
     */
    public Dictionary() throws IOException, SQLException {
        words = new Trie();
        db = new DictionaryDatabase();
        db.createTables();

        for (String word : db.getAllWords()) {
            words.insert(word);
        }
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word The Word object to add.
     */
    public void add(Word word) {
        words.insert(word.getTarget());

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

    public WordExplain getWordExplain(String word) throws SQLException {
        return getDatabase().getWordExplain(word);
    }

    public DictionaryDatabase getDatabase() {
        return db;
    }

    public static void main(final String[] args) {
        try {
            Dictionary dict = new Dictionary();
            dict.add(new Word("hello world", new WordExplain("noun", "helo", "chao the gioi", "hey hello world", "vi"),
                    Language.ENGLISH));
            System.out.println(dict.getWordExplain("hello world").getMeaning());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
