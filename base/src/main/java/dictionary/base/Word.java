package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Word {
    private String word_id;
    private String word;
    private String pronunce;

    public Word(final String word, final String pronunce) {
        this(null, word, pronunce);
    }

    public Word(final String word_id, final String word, final String pronunce) {
        this.word_id = word_id;
        this.word = word;
        this.pronunce = pronunce;
    }

    public Word(final ResultSet resultSet) throws SQLException {
        this(resultSet.getString("word_id"),
                resultSet.getString("word"),
                resultSet.getString("pronunce"));
    }

    public String getWordID() {
        return word_id;
    }

    public void setWordID(final String word_id) {
        this.word_id = word_id;
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
