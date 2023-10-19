package dictionary.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {
    private String example_id;
    private String explain_id;
    private String example;
    private String translate;

    public Example(String example_id, String explain_id, String example, String translate) {
        this.example_id = example_id;
        this.explain_id = explain_id;
        this.example = example;
        this.translate = translate;
    }

    public Example(ResultSet resultSet) throws SQLException {
        this(resultSet.getString("example_id"),
                resultSet.getString("example_id"),
                resultSet.getString("example"),
                resultSet.getString("translate"));
    }

    public String getExample_id() {
        return example_id;
    }

    public void setExample_id(String example_id) {
        this.example_id = example_id;
    }

    public String getExplain_id() {
        return explain_id;
    }

    public void setExplain_id(String explain_id) {
        this.explain_id = explain_id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
