package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {
    private String example_id;
    private String explain_id;
    private String example;
    private String translate;

    public Example(final String explain_id, final String example, final String translate) {
        this(null, explain_id, example, translate);
    }

    public Example(final String example_id, final String explain_id, final String example, final String translate) {
        this.example_id = example_id;
        this.explain_id = explain_id;
        this.example = example;
        this.translate = translate;
    }

    public Example(final ResultSet resultSet) throws SQLException {
        this(resultSet.getString("example_id"),
                resultSet.getString("example_id"),
                resultSet.getString("example"),
                resultSet.getString("translate"));
    }

    public String getExampleID() {
        return example_id;
    }

    public void setExampleID(final String example_id) {
        this.example_id = example_id;
    }

    public String getExplainID() {
        return explain_id;
    }

    public void setExplainID(final String explain_id) {
        this.explain_id = explain_id;
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
