package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Explain {
    private String explain_id;
    private String word_id;
    private String type;
    private String meaning;

    public Explain(String explain_id, String word_id, String type, String meaning) {
        this.explain_id = explain_id;
        this.word_id = word_id;
        this.type = type;
        this.meaning = meaning;
    }

    public Explain(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("explain_id"),
                resultSet.getString("word_id"),
                resultSet.getString("type"),
                resultSet.getString("meaning"));
    }

    public String getExplainID() {
        return explain_id;
    }

    public void setExplainID(String explain_id) {
        this.explain_id = explain_id;
    }

    public String getWordID() {
        return word_id;
    }

    public void setWordID(String word_id) {
        this.word_id = word_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
