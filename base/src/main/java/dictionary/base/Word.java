package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Word {
    private String word_id;
    private String word;
    private String pronunce;

    public Word(String word_id, String word, String pronunce) {
        this.word_id = word_id;
        this.word = word;
        this.pronunce = pronunce;
    }

    public Word(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("word_id"),
                resultSet.getString("word"),
                resultSet.getString("pronunce"));
    }

    public String getWord_id() {
        return word_id;
    }

    public void setWord_id(String word_id) {
        this.word_id = word_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunce() {
        return pronunce;
    }

    public void setPronunce(String pronunce) {
        this.pronunce = pronunce;
    }
}
