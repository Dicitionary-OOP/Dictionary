package dictionary.base;

import dictionary.base.algorithm.trie.Trie;
import dictionary.base.database.DictionaryDatabase;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dictionary {
    private static Dictionary INSTANCE;
    private DictionaryDatabase database;
    private Trie words;

    public static Dictionary getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Dictionary();
        }

        return INSTANCE;
    }

    public Dictionary() {
        try {
            words = new Trie();
            database = new DictionaryDatabase();
            for (ArrayList<String> word : getDatabase().getAllWords()) {
                words.insert(word.get(0), word.get(1));
            }
        } catch (Exception e) {
        }
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word The Word object to add.
     */
    public void add(Word word) {
        words.insert(word.getWord(), word.getWordID());

        try {
            database.addWord(word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param word The Word object to remove.
     */
    public void remove(Word word) {
        words.remove(word.getWord());

        try {
            database.removeWord(word.getWordID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of all words in the dictionary.
     *
     * @return An ArrayList of all words in the dictionary.
     */
    public ArrayList<ArrayList<String>> getAllWords() {
        return lookup("");
    }

    /**
     * Looks up words in the dictionary with a given prefix.
     *
     * @param lookupWord The prefix to search for.
     * @return An ArrayList of words that match the given prefix.
     */
    public ArrayList<ArrayList<String>> lookup(final String lookupWord) {
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

    public DictionaryDatabase getDatabase() {
        return database;
    }

    public Boolean isExistWord(String word){
        return words.getEndNode(word) != null;
    }

    public String getRandomWordByLength(int length) {
        try {
            return database.getRandomWordByLength(length);
        } catch(Exception e) {
            return null;
        }
    }
}
