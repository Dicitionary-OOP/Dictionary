package base.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Explain {
    private String explainID;
    private String wordID;
    private String type;
    private String meaning;

    public Explain(final String wordID, final String type, final String meaning) {
        this(null, wordID, type, meaning);
    }

    public Explain(final String explainID, final String wordID, final String type, final String meaning) {
        this.explainID = explainID;
        this.wordID = wordID;
        this.type = type;
        this.meaning = meaning;
    }

    public Explain(final ResultSet resultSet) throws SQLException {
        this(resultSet.getString("explain_id"),
                resultSet.getString("word_id"),
                resultSet.getString("type"),
                resultSet.getString("meaning"));
    }

    public String getExplainID() {
        return explainID;
    }

    public void setExplainID(final String explainID) {
        this.explainID = explainID;
    }

    public String getWordID() {
        return wordID;
    }

    public void setWordID(final String wordID) {
        this.wordID = wordID;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(final String meaning) {
        this.meaning = meaning;
    }
}
