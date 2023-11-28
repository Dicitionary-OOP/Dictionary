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

    public ArrayList<ArrayList<String>> getAllWords() throws SQLException {
        final ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
        final ResultSet resultSet = executeQuery("SELECT * FROM words");
        while (resultSet.next()) {
            final String word = resultSet.getString("word");
            final String word_id = resultSet.getString("word_id");
            final ArrayList<String> curWord = new ArrayList<>();
            curWord.add(word);
            curWord.add(word_id);
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

        final ResultSet resultSet = executeQuery("SELECT MAX(word_id) as word_id FROM words LIMIT 1 ");
        return resultSet.getString("word_id");
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

        final ResultSet resultSet = executeQuery("SELECT MAX(explain_id) as explain_id FROM explains LIMIT 1 ");
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
