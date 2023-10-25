package dictionary.base.database;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dictionary.base.Example;
import dictionary.base.Word;
import dictionary.base.Explain;
import dictionary.base.utils.Utils;

public class DictionaryDatabase extends Database {
    private final static String DATABASE_PATH = Utils.getResource("/database/en-vi.db");

    public DictionaryDatabase() throws SQLException, URISyntaxException {
        super(DATABASE_PATH);
    }

    public ArrayList<String> getAllWords() throws SQLException {
        final ArrayList<String> words = new ArrayList<>();
        final ResultSet resultSet = executeQuery("SELECT * FROM words");
        while (resultSet.next()) {
            words.add(resultSet.getString("word"));
        }

        return words;
    }

    public ArrayList<Example> getExamples(final String explainID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT *");
        query.append("FROM examples ");
        query.append("GROUP BY explain_id");
        query.append("WHERE explain_id = ?");
        final ArrayList<Example> examples = new ArrayList<>();
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, explainID);

        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            examples.add(new Example(resultSet));
        }

        return examples;
    }

    public ArrayList<Explain> getExplainsByWordID(final String wordID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM explains ");
        query.append("WHERE word_id = ? ");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, wordID);

        final ArrayList<Explain> explains = new ArrayList<>();
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            explains.add(new Explain(resultSet));
        }

        return explains;
    }

    public ArrayList<String> getWordsStartWith(final String prefix) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM words ");
        query.append("WHERE words.word LIKE ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, prefix + "%");

        final ArrayList<String> words = new ArrayList<>();
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            words.add(resultSet.getString("word"));
        }

        return words;
    }

    public void addWord(final Word word) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO words (word, pronounce) ");
        query.append("VALUES (?, ?)");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, word.getWord());
        preparedStatement.setString(2, word.getPronunce());
        preparedStatement.executeUpdate();
    }

    public void removeWord(final String wordID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM words ");
        query.append("WHERE word_id = ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, wordID);
        preparedStatement.executeUpdate();
    }

    /**
     * Add explain of the word.
     *
     * @param explain - the new explain(giải thích của từ).
     * @param wordID  - wordID (id từ được giải thích).
     *
     * @throws SQLException
     */

    public void addExplain(final Explain explain, final String wordID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO explains (word_id, type, meaning) ");
        query.append("VALUES (?, ?, ?)");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, wordID);
        preparedStatement.setString(2, explain.getType());
        preparedStatement.setString(3, explain.getMeaning());
        preparedStatement.executeUpdate();
    }

    /**
     * Remove the explain.
     *
     * @param explainID - the removed explain(từ bị xóa)
     *
     * @throws SQLException
     */
    public void removeExplain(final String explainID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM explains ");
        query.append("WHERE explain_id= ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, explainID);
        preparedStatement.executeUpdate();
    }

    /**
     * Adds a new example to an existing explanation (explain).
     * / Thêm một ví dụ vào một giải thích từ.
     * Note that the example's ID must be null. If it is not,
     * that implies the example already exists in the database,
     * and thus should be updated, NOT added.
     * / Chú ý ID của đối tượng example phải là null.
     *
     * @param example - An Example object.
     * @throws SQLException
     */
    public void addExample(final Example example) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO examples (explain_id, example, translate) VALUES (?, ?, ?)");
        preparedStatement.setString(1, example.getExplainID());
        preparedStatement.setString(2, example.getExample());
        preparedStatement.setString(3, example.getTranslate());
        preparedStatement.executeUpdate();
    }

    /**
     * Removes an example from the database.
     * / Xoá một ví dụ khỏi CSDL.
     *
     * @param exampleID - The ID of the example to be removed.
     * @throws SQLException
     */
    public void removeExample(final String exampleID) throws SQLException {
        final PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM examples WHERE example_id = ?");
        preparedStatement.setString(1, exampleID);
        preparedStatement.executeUpdate();
    }
}
