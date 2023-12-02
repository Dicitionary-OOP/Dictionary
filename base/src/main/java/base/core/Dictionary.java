package base.core;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.math3.util.Pair;

import base.algorithm.trie.Trie;
import base.database.Database;

public class Dictionary {
    private static Dictionary INSTANCE;
    private final Database database;
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

    private Dictionary() throws SQLException, URISyntaxException {
        words = new Trie();
        database = new Database();

        for (final Pair<String, String> word : getDatabase().getAllWords()) {
            words.insert(word.getKey(), word.getValue());
        }
    }

    public String addWord(final Word word) throws SQLException {
        final String wordID = database.addWord(word);
        words.insert(word.getWord(), wordID);
        return wordID;
    }

    public void removeWord(final Word word) throws SQLException {
        words.remove(word.getWord());
        database.removeWord(word.getWordID());
    }

    public String addExplain(final Explain explain) throws SQLException {
        return database.addExplain(explain);
    }

    public void removeExplain(final String explainID) throws SQLException {
        database.removeExplain(explainID);
    }

    public void addExample(final Example example) throws SQLException {
        database.addExample(example);
    }

    public void removeExample(final String exampleID) throws SQLException {
        database.removeExplain(exampleID);
    }

    public ArrayList<Pair<String, String>> getAllWords() {
        return lookup("");
    }

    public ArrayList<Pair<String, String>> lookup(final String lookupWord) {
        return words.getAllWordsStartWith(lookupWord);
    }

    public void removeWord(final String word) {
        words.remove(word);
    }

    public Database getDatabase() {
        return database;
    }

    public Boolean isExistWord(final String word) {
        return words.getEndNode(word) != null;
    }

    public String getRandomWordByLength(final int length) throws SQLException {
        return database.getRandomWordByLength(length);
    }

}
