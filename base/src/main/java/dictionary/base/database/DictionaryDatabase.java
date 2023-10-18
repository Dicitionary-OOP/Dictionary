package dictionary.base.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dictionary.base.Example;
import dictionary.base.Word;
import dictionary.base.WordExplain;
import dictionary.base.utils.Utils;

public class DictionaryDatabase extends Database {
    private final static String DATABASE_PATH = Utils.getResource("/database/en-vi.db");

    public DictionaryDatabase() throws SQLException {
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
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.getExamples());
        preparedStatement.setString(1, explain_id);

        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            final String example = resultSet.getString("example");
            final String translate = resultSet.getString("translate");
            examples.add(new Example(example, translate));
        }

        return examples;
    }

    public ArrayList<WordExplain> getWordExplain(final String word) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(Statement.getWordExplains());
        preparedStatement.setString(1, word);

        final ArrayList<WordExplain> explains = new ArrayList<>();
        final ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            final String type = resultSet.getString("type");
            final String meaning = resultSet.getString("meaning");
            final String explain_id = resultSet.getString("explain_id");
            explains.add(new WordExplain(type, meaning, getExamples(explain_id)));
        }

        return explains;
    }

    public void addWord(final Word word) throws SQLException {
        // TODO
    }

    public void addExplain(final WordExplain explain, final String word) throws SQLException {
        // TODO
    }

    public void addExample(final Example example, final String example_id) throws SQLException {
        // TODO
    }

    public void findAllWordsStartWith(final String prefix) throws SQLException {
        // TODO
    }
}
