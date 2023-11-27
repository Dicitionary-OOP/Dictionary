package dictionary.base;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

import dictionary.base.algorithm.trie.Trie;
import dictionary.base.database.DictionaryDatabase;

public class Dictionary {
    private static Dictionary INSTANCE;
    private final DictionaryDatabase database;
    private final Trie words;

    public static Dictionary getInstance() {
        if (INSTANCE == null) {
            synchronized (Dictionary.class) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = new Dictionary();
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return INSTANCE;
    }

    private Dictionary() throws SQLException, URISyntaxException  {
        words = new Trie();
        database = new DictionaryDatabase();

        for (final ArrayList<String> word : getDatabase().getAllWords()) {
            words.insert(word.get(0), word.get(1));
        }
    }

    public void add(final Word word) {
        words.insert(word.getWord(), word.getWordID());

        try {
            database.addWord(word);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(final Word word) {
        words.remove(word.getWord());

        try {
            database.removeWord(word.getWordID());
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getAllWords() {
        return lookup("");
    }

    public ArrayList<ArrayList<String>> lookup(final String lookupWord) {
        return words.getAllWordsStartWith(lookupWord);
    }

    public void removeWord(final String word) {
        words.remove(word);
    }

    public DictionaryDatabase getDatabase() {
        return database;
    }

    public Boolean isExistWord(final String word) {
        return words.getEndNode(word) != null;
    }

    public String getRandomWordByLength(final int length) {
        try {
            return database.getRandomWordByLength(length);
        } catch (final Exception e) {
            return null;
        }
    }
}
