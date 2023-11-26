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
import edu.cmu.sphinx.fst.utils.Pair;

public class DictionaryDatabase extends Database {
    private final static String DATABASE_PATH = Utils.getResource("/database/en-vi.db");

    public DictionaryDatabase() throws SQLException, URISyntaxException {
        super(DATABASE_PATH);
    }

    public ArrayList<ArrayList<String>> getAllWords() throws SQLException {
        final ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
        final ResultSet resultSet = executeQuery("SELECT * FROM words");
        while (resultSet.next()) {
            String word = resultSet.getString("word");
            String word_id = resultSet.getString("word_id");
            ArrayList<String> curWord = new ArrayList<>();
            curWord.add(word);
            curWord.add(word_id);
            // The current word here have 2 attributes: curWord[0] mean the word, curWord[1]
            // mean the word_id
            words.add(curWord);
        }

        return words;
    }

    public Word getWordByWordID(final String wordID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM words ");
        query.append("WHERE word_id = ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, wordID);

        final ResultSet resultSet = preparedStatement.executeQuery();
        final Word word = new Word(resultSet);

        return word;
    }

    public ArrayList<Example> getExamples(final String explainID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM examples ");
        query.append("WHERE explain_id = ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, explainID);

        final ArrayList<Example> examples = new ArrayList<>();
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

    public String getRandomWordByLength(final int length) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT * ");
        query.append("FROM words ");
        query.append("WHERE LENGTH(word) = ? ");
        query.append("ORDER BY RANDOM() LIMIT 1;");

        final PreparedStatement preparedStatement = connection
                .prepareStatement(query.toString());
        preparedStatement.setInt(1, length);
        preparedStatement.executeQuery();
        return preparedStatement.executeQuery().getString("word");
    }
}
