package base.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import base.core.Example;
import base.core.Word;
import base.core.Explain;
import base.utils.Utils;
import org.apache.commons.math3.util.Pair;

public class Database {
    private final Connection connection;
    private final Statement statement;

    private final static String DATABASE_PATH = Utils.getResource("/database/en-vi.db");

    public Database() throws SQLException, URISyntaxException {
        connection = DriverManager.getConnection(new URI("jdbc:sqlite", null, DATABASE_PATH, null).toString());
        statement = connection.createStatement();
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

    public ArrayList<Pair<String, String>> getAllWords() throws SQLException {
        final ArrayList<Pair<String, String>> words = new ArrayList<Pair<String, String>>();
        final ResultSet resultSet = statement.executeQuery("SELECT * FROM words");
        while (resultSet.next()) {
            final String word = resultSet.getString("word");
            final String word_id = resultSet.getString("word_id");
            final Pair<String, String> curWord = new Pair<>(word, word_id);
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

    public Word getWordObjectByWord(final String wordString) throws SQLException {
        final String sql = "SELECT * FROM words WHERE word = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, wordString);

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

    public String addWord(final Word word) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO words (word, pronounce) ");
        query.append("VALUES (?, ?) ");

        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, word.getWord());
        preparedStatement.setString(2, word.getPronunce());
        final int rowEffect = preparedStatement.executeUpdate();
        if (rowEffect == 0) {
            return null;
        }

        final ResultSet resultSet = statement.executeQuery("SELECT MAX(word_id) as word_id FROM words LIMIT 1 ");
        return resultSet.getString("word_id");
    }

    public void updateWord(final String wordID, final Word word) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("UPDATE words");
        query.append("SET");
        query.append("word = ?");
        query.append("pronounce = ?");
        query.append("WHERE word_id = ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, word.getWord());
        preparedStatement.setString(2, word.getPronunce());
        preparedStatement.setString(3, wordID);
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

    public String addExplain(final Explain explain) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO explains (word_id, type, meaning) ");
        query.append("VALUES (?, ?, ?)");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, explain.getWordID());
        preparedStatement.setString(2, explain.getType());
        preparedStatement.setString(3, explain.getMeaning());
        final int rowEffect = preparedStatement.executeUpdate();
        if (rowEffect == 0) {
            return null;
        }

        final ResultSet resultSet = statement
                .executeQuery("SELECT MAX(explain_id) as explain_id FROM explains LIMIT 1 ");
        return resultSet.getString("explain_id");
    }

    public void removeExplain(final String explainID) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("DELETE FROM explains ");
        query.append("WHERE explain_id= ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, explainID);
        preparedStatement.executeUpdate();
    }

    public void updateExplain(final String explainID, final Explain explain) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("UPDATE explains");
        query.append("SET");
        query.append("word_id = ? ,");
        query.append("type = ? ,");
        query.append("meaning = ?");
        query.append("WHERE explain_id = ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, explain.getWordID());
        preparedStatement.setString(2, explain.getType());
        preparedStatement.setString(3, explain.getMeaning());
        preparedStatement.setString(4, explainID);
        preparedStatement.executeUpdate();
    }

    public void addExample(final Example example) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO examples (explain_id, example, translate) VALUES (?, ?, ?)");
        preparedStatement.setString(1, example.getExplainID());
        preparedStatement.setString(2, example.getExample());
        preparedStatement.setString(3, example.getTranslate());
        preparedStatement.executeUpdate();
    }

    public void removeExample(final String exampleID) throws SQLException {
        final PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM examples WHERE example_id = ?");
        preparedStatement.setString(1, exampleID);
        preparedStatement.executeUpdate();
    }

    public void updateExample(final String exampleID, final Example example) throws SQLException {
        final StringBuilder query = new StringBuilder();
        query.append("UPDATE examples");
        query.append("SET");
        query.append("explain_id = ? ,");
        query.append("example = ? ,");
        query.append("translate = ?");
        query.append("WHERE example_id = ?");
        final PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, example.getExampleID());
        preparedStatement.setString(2, example.getExample());
        preparedStatement.setString(3, example.getTranslate());
        preparedStatement.setString(4, exampleID);
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
