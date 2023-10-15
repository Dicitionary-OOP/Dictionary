package dictionary.base;

import dictionary.base.algorithm.trie.Trie;
import dictionary.base.database.DictionaryDatabase;
import dictionary.base.utils.Utils;

import java.util.ArrayList;

public class Dictionary {
    private final Trie words;

    /**
     * Initializes a new Dictionary with an empty Trie.
     */
    public Dictionary() {
        words = new Trie();
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word The Word object to add.
     */
    public void add(String word) {
        words.add(word);
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
        return words.getWordsStartWithPrefix(lookupWord);
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param word The Word object to remove.
     */
    public void removeWord(final String word) {
        words.remove(word);
    }

    public static void main(final String[] args) {
        try {
            final DictionaryDatabase db = new DictionaryDatabase(Utils.getResource("/database/database.db"));
            db.createTables();
            db.executeUpdate("INSERT INTO words(word,lang_id) VALUES('helloword','en')");
            db.executeUpdate("INSERT INTO words(word,lang_id) VALUES('hallo','en')");
            db.executeUpdate("INSERT INTO words(word,lang_id) VALUES('hahah','en')");
            db.executeUpdate("INSERT INTO words(word,lang_id) VALUES('haha','en')");
            db.executeUpdate("INSERT INTO words(word,lang_id) VALUES('he','en')");
            db.executeUpdate("INSERT INTO words(word,lang_id) VALUES('goodmorning','en')");

            Dictionary dict = new Dictionary();
            for (String word : db.getAllWords()) {
                System.out.println(word);
                dict.add(word);
            }

            System.out.println("LOOKUP");
            for (String word : dict.lookup("ha")) {
                System.out.println(word);
            }

            db.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
