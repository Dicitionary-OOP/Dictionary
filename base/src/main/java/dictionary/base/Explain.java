package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Explain {
    private String explainID;
    private String wordID;
    private String type;
    private String meaning;

    public Explain(String wordID, String type, String meaning) {
        this(null, wordID, type, meaning);
    }

    public Explain(String explainID, String wordID, String type, String meaning) {
        this.explainID = explainID;
        this.wordID = wordID;
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
        return explainID;
    }

    public void setExplainID(String explainID) {
        this.explainID = explainID;
    }

    public String getWordID() {
        return wordID;
    }

    public void setWordID(String wordID) {
        this.wordID = wordID;
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
