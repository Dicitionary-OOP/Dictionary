package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Word {
    private String wordID;
    private String word;
    private String pronunce;

    public Word(final String word, final String pronunce) {
        this(null, word, pronunce);
    }

    public Word(final String wordID, final String word, final String pronunce) {
        this.wordID = wordID;
        this.word = word;
        this.pronunce = pronunce;
    }

    public Word(final ResultSet resultSet) throws SQLException {
        this(resultSet.getString("word_id"),
                resultSet.getString("word"),
                resultSet.getString("pronounce"));
    }

    public String getWordID() {
        return wordID;
    }

    public void setWordID(final String wordID) {
        this.wordID = wordID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public String getPronunce() {
        return pronunce;
    }

    public void setPronunce(final String pronunce) {
        this.pronunce = pronunce;
    }
}
