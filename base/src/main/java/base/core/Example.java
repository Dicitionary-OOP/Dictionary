package base.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {
    private String exampleID;
    private String explainID;
    private String example;
    private String translate;

    public Example(final String explainID, final String example, final String translate) {
        this(null, explainID, example, translate);
    }

    public Example(final String exampleID, final String explainID, final String example, final String translate) {
        this.exampleID = exampleID;
        this.explainID = explainID;
        this.example = example;
        this.translate = translate;
    }

    public Example(final ResultSet resultSet) throws SQLException {
        this(resultSet.getString("example_id"),
                resultSet.getString("explain_id"),
                resultSet.getString("example"),
                resultSet.getString("translate"));
    }

    public String getExampleID() {
        return exampleID;
    }

    public void setExampleID(final String exampleID) {
        this.exampleID = exampleID;
    }

    public String getExplainID() {
        return explainID;
    }

    public void setExplainID(final String explainID) {
        this.explainID = explainID;
    }

    public String getExample() {
        return example;
    }

    public void setExample(final String example) {
        this.example = example;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(final String translate) {
        this.translate = translate;
    }
}
