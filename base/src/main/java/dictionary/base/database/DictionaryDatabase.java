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

    public DictionaryDatabase() throws SQLException , URISyntaxException {
        super(DATABASE_PATH);
    }

    public ArrayList<String> getAllWords() throws SQLException {
        final ArrayList<String> words = new ArrayList<>();
        final ResultSet resultSet = executeQuery(Statement.getAllWord());
        while (resultSet.next()) {
            words.add(resultSet.getString("word"));
        }

        return words;
    }

    public ArrayList<Example> getExamples(final String explain_id) throws SQLException {
        final ArrayList<Example> examples = new ArrayList<>();
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.getExamplesByExplainID());
        preparedStatement.setString(1, explain_id);

        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            examples.add(new Example(resultSet));
        }

        return examples;
    }

    public ArrayList<Explain> getWordExplain(final String word) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.getExplainsByWord());
        preparedStatement.setString(1, word);

        final ArrayList<Explain> explains = new ArrayList<>();
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            explains.add(new Explain(resultSet));
        }

        return explains;
    }

    public ArrayList<String> getWordsStartWith(final String prefix) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.getWordsLike());
        preparedStatement.setString(1, prefix + "%");

        final ArrayList<String> words = new ArrayList<>();
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            words.add(resultSet.getString("word"));
        }
        return words;
    }

    public void addWord(final Word word) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.addWord());
        preparedStatement.setString(1, word.getWord());
        preparedStatement.setString(2, word.getPronunce());

        preparedStatement.executeUpdate();
    }

    public void removeWord(String wordID) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.removeWord());
        preparedStatement.setString(1, wordID);

        preparedStatement.executeUpdate();
    }

    /**.
     *Add explain of the word.
     *
     * @param explain - the new explain(giải thích của từ).
     * @param word - the added word(từ được giải thích).
     *
     * @throws SQLException
     */

    public void addAnExplain(final Explain explain, final Word word) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.addAnExplain());
        preparedStatement.setString(1, word.getWordID());
        preparedStatement.setString(2, explain.getType());
        preparedStatement.setString(3, explain.getMeaning());

        preparedStatement.executeUpdate();
    }

    /**.
     *Remove the explain.
     *
     * @param explainID - the removed explain(từ bị xóa)
     * @throws SQLException
     */
    public void removeAnExplain(String explainID) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.removeAnExplain());
        preparedStatement.setString(1, explainID);

        preparedStatement.executeUpdate();
    }

    public void addExample(final Example example, final String example_id) throws SQLException {
        // TODO
    }
}
